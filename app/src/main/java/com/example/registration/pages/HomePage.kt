package com.example.registration.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.registration.navigation.NewsArticlePage
import com.example.registration.viewmodels.AuthViewModel
import com.example.registration.viewmodels.NewsViewModel
import com.example.registration.network.Article
import com.example.registration.viewmodels.NavItem
import com.example.registration.viewmodels.NewsDBViewModel

//@Preview(device = "spec:width=411dp,height=891dp", showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    newsViewModel: NewsViewModel,
    newsDBViewModel: NewsDBViewModel = viewModel()
) {
    val authState = authViewModel.authState.observeAsState()
    val articles by newsViewModel.articles.observeAsState(emptyList())
    val favoriteUrls by newsDBViewModel.favoriteUrls.collectAsStateWithLifecycle()

//    LaunchedEffect(authState.value) {
//        when (authState.value) {
//            is AuthState.UnAuthenticated -> navController.navigate(SignupPage)
//            else -> Unit
//        }
//    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ) {
        CategoriesBar(newsViewModel)
        LazyColumn(
            modifier = Modifier,
//                .padding(start = 16.dp, end = 16.dp)
            contentPadding = PaddingValues(1.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                articles,
                key = { it.url ?: it.title ?: it.hashCode() }
            ) { article ->
                NewsCard(
                    article = article,
                    navController = navController,
                    isFavorite = article.url in favoriteUrls,
                    onToggleFavorite = { newsDBViewModel.toggle(article) }
                )
            }
        }
    }
}


@Composable
fun NewsCard(
    article: Article,
    navController: NavController,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit
) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F9FC)),
        onClick = {
            article.url
                ?.takeIf { it.startsWith("https://") }
                ?.let { navController.navigate(NewsArticlePage(it)) }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Icon(
                imageVector = if (isFavorite) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                contentDescription = "Bookmark",
                tint = Color(0xFF6A4C93),
                modifier = Modifier
                    .padding(12.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.9f))
                    .clickable(onClick = onToggleFavorite)
                    .padding(8.dp)
            )

            AsyncImage(
                model = article.urlToImage
                    ?: "https://png.pngtree.com/png-vector/20190820/ourmid/pngtree-no-image-vector-illustration-isolated-png-image_1694547.jpg",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .height(180.dp)
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(8.dp))
            )
            Text(
                modifier = Modifier.padding(top = 20.dp),
                text = article.title ?: "No title available",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )
            Row(
                modifier = Modifier.padding(top = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = article.source?.name ?: "Unknown",
                    color = Color.Gray
                )
                Text(
                    text = article.publishedAt?: "",
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    modifier = Modifier
                        .background(color = Color(0xFFF8F9FC))
                        .clickable(
                            onClick = {
                                ShareCompat
                                    .IntentBuilder(context)
                                    .setType("text/plain")
                                    .setChooserTitle("Share article with: ")
                                    .setText("article.url")
                                    .startChooser()
                            }
                        )
                )
            }

//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .align(Alignment.BottomStart)
//                    .height(100.dp)
//                    .background(
//                        Brush.verticalGradient(
//                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
//                        )
//                    )
//            )


        }
    }
}

@Composable
fun CategoriesBar(newsViewModel: NewsViewModel) {
    val categories = listOf(
        "General",
        "Business",
        "Entertainment",
        "Health",
        "Science",
        "Sports",
        "Technology"
    )

    var searchQuery by rememberSaveable() { mutableStateOf("") }
    var isExpanded by rememberSaveable { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (isExpanded) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(8.dp)
                    .height(48.dp)
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
                    .clip(CircleShape),
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                },
                placeholder = {
                    Text(text = "Search")
                },
                trailingIcon = {
                    IconButton(onClick = {
                        isExpanded = false
                        if (searchQuery.isNotBlank()) {
                            newsViewModel.searchNews(searchQuery)
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = null)
                    }
                }
            )
        } else {
            IconButton(onClick = {
                isExpanded = true
            }) {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        }

        categories.forEach { category ->
            Button(
                onClick = {
                    newsViewModel.loadNews(category = category.lowercase())
                },
                modifier = Modifier.padding(4.dp)
            ) {
                Text(text = category)
            }
        }
    }
}
