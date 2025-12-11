package com.example.citaesdiariarias

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface QuoteApi {
    // COLE AQUI A PARTE FINAL DA SUA URL (depois do .com/)
    // Exemplo: "RicardoFiorini/1234abc/raw/5678def/quotes.json"
    @GET("RicardoFiorini/9588727e9397bee18979f0338eae6242/raw/60f3274c67ee14a9fe63aa84f9c9ac68b0df198d/quotes.json")
    suspend fun getQuotes(): List<Quote>
}

object RetrofitInstance {
    val api: QuoteApi by lazy {
        Retrofit.Builder()
            // A base sempre termina com barra
            .baseUrl("https://gist.githubusercontent.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(QuoteApi::class.java)
    }
}