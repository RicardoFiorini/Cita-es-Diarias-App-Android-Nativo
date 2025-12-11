package com.example.citaesdiariarias

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "favorites_table")
data class Quote(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    // Mudamos os nomes aqui para bater com seu JSON novo
    @SerializedName("text") val text: String,
    @SerializedName("author") val author: String
)