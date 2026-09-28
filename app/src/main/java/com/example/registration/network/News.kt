package com.example.registration.network

import androidx.room3.Entity

data class News(
    val articles: List<Article> = emptyList()
)

data class Article(
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
)

data class Source(
    val id: String?,
    val name: String?
)