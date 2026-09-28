package com.example.registration.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.registration.R
import com.example.registration.database.FavoriteDB
import com.example.registration.viewmodels.NewsDBViewModel

@Composable
fun BookmarkPage(
    modifier: Modifier = Modifier,
    newsDBViewModel: NewsDBViewModel = viewModel()
) {
    val favoritesFlow = remember(newsDBViewModel) { newsDBViewModel.getAllArticles() }

    val favorites by favoritesFlow
        .collectAsStateWithLifecycle(initialValue = emptyList())

    val favoriteUrls by newsDBViewModel.favoriteUrls.collectAsStateWithLifecycle()

    if (favorites.isEmpty()) {
        Box(modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No bookmarks yet", color = Color.Gray)
        }
    }else{
    LazyColumn(
        modifier.fillMaxSize(),
        contentPadding = PaddingValues(1.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(
            favorites,
            key = { it.url }
        ) { article ->
            FavoriteCard(
                favorite = article,
                isFavorite = article.url in favoriteUrls,
                onToggleFavorite = { newsDBViewModel.toggle(article) }
            )
        }
    }
    }
}


@Composable
fun FavoriteCard(
    favorite: FavoriteDB,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    ) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = {})
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AsyncImage(
            model = favorite.urlToImage ?:"https://png.pngtree.com/png-vector/20190820/ourmid/pngtree-no-image-vector-illustration-isolated-png-image_1694547.jpg",
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(text = favorite.title?:"not")
            Text(
                text = favorite.sourceName?:"dede",
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Icon(
                imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark",
                tint = Color(0xFF6A4C93),
                modifier = Modifier
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.9f))
                    .clickable(onClick = onToggleFavorite)
                    .padding(8.dp)
            )

        }
    }

}