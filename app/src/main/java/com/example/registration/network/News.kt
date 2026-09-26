package com.example.registration.network

data class News(
    val articles: List<Article> = emptyList()
)

data class Article(
    val title: String?,
    val url: String?,
    val urlToImage: String?,
    val author: String?,
    val publishedAt: String?,
)