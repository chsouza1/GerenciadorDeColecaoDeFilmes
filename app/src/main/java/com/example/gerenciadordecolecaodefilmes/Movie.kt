package com.example.gerenciadordecolecaodefilmes

data class Movie(
    val id: Long = 0,
    val title: String,
    val year: Int,
    val genre: String,
    val collectionId: Long
)
