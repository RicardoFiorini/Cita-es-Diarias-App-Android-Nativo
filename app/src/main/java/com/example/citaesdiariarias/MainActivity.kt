package com.example.citaesdiariarias

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen // <--- IMPORT NOVO DA SPLASH
import androidx.lifecycle.lifecycleScope
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var tvQuote: TextView
    private lateinit var tvAuthor: TextView
    private lateinit var btnShare: ImageButton
    private lateinit var btnFavorite: ImageButton
    private lateinit var btnNext: ImageButton

    private var currentQuote: Quote? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        // A SPLASH SCREEN VEM AQUI, ANTES DE TUDO:
        installSplashScreen()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1. Vincular componentes da tela
        tvQuote = findViewById(R.id.tvQuote)
        tvAuthor = findViewById(R.id.tvAuthor)
        btnShare = findViewById(R.id.btnShare)
        btnFavorite = findViewById(R.id.btnFavorite)
        btnNext = findViewById(R.id.btnNext)

        // 2. Carregar a primeira frase
        fetchNewQuote()

        // 3. Configurar botões
        btnNext.setOnClickListener {
            fetchNewQuote()
        }

        btnShare.setOnClickListener {
            currentQuote?.let { quote ->
                val shareIntent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "\"${quote.text}\" - ${quote.author}")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(shareIntent, "Compartilhar Inspiração"))
            }
        }

        btnFavorite.setOnClickListener {
            currentQuote?.let { quote ->
                saveQuoteToFavorites(quote)
            }
        }

        // 4. Agendar Notificação Diária
        setupDailyNotification()
    }

    private fun fetchNewQuote() {
        tvQuote.text = "Carregando reflexão..."
        tvAuthor.text = ""

        lifecycleScope.launch {
            try {
                // Agora pegamos a lista completa do seu JSON no GitHub
                val response = RetrofitInstance.api.getQuotes()

                if (response.isNotEmpty()) {
                    // SORTEAMOS UMA FRASE AQUI NO APP
                    currentQuote = response.random()

                    tvQuote.text = "\"${currentQuote!!.text}\""
                    tvAuthor.text = currentQuote!!.author
                }
            } catch (e: Exception) {
                tvQuote.text = "Verifique sua conexão."
                e.printStackTrace()
            }
        }
    }

    private fun saveQuoteToFavorites(quote: Quote) {
        lifecycleScope.launch {
            try {
                val db = AppDatabase.getDatabase(applicationContext)
                db.quoteDao().insert(quote)
                Toast.makeText(this@MainActivity, "Salvo nos favoritos!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Erro ao salvar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupDailyNotification() {
        val workRequest = PeriodicWorkRequestBuilder<DailyQuoteWorker>(24, TimeUnit.HOURS).build()
        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "DailyQuoteWork",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}