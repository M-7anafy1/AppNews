package com.example.registration.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomePage
@Serializable
object LoginPage
@Serializable
object SignupPage
@Serializable
data class NewsArticlePage(val url: String)


