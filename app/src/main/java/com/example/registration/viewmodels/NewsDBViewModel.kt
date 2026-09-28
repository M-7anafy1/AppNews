package com.example.registration.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.registration.database.FavoriteDB
import com.example.registration.database.RoomDBHelper
import com.example.registration.database.toFavorite
import com.example.registration.network.Article
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlin.collections.emptySet

class NewsDBViewModel(app: Application) : AndroidViewModel(app) {
    private val db = RoomDBHelper.getInstance(app)

    fun getAllArticles() = db.dao.getAllArticles()

    val favoriteUrls: StateFlow<Set<String>> = db.dao.getFavoriteUrls()
        .map { it.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), emptySet())

    fun toggle(article: Article) {
        val favorite = article.toFavorite() ?: return
        viewModelScope.launch {
            val current = db.dao.getFavoriteUrls().first()
            if (favorite.url in current) db.dao.delete(favorite)
            else db.dao.upsert(favorite)
        }
    }

    fun toggle(favorite: FavoriteDB) {
        viewModelScope.launch {
            val current = db.dao.getFavoriteUrls().first()
            if (favorite.url in current) db.dao.delete(favorite)
            else db.dao.upsert(favorite)
        }
    }
}
