package com.example.registration.database

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.registration.network.Article
@Entity(tableName = "favorites")
data class FavoriteDB(
    @PrimaryKey
    val url: String,
    val title: String?,
    val urlToImage: String?,
    val sourceName: String?,
    val publishedAt: String?
)

fun Article.toFavorite(): FavoriteDB? {
    val safeUrl = url ?: return null
    return FavoriteDB(safeUrl, title, urlToImage, source?.name, publishedAt)
}
