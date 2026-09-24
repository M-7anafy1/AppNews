package com.example.registration.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ShareCompat
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.registration.viewmodels.AuthViewModel
import com.example.registration.viewmodels.NewsViewModel
import com.example.registration.network.Article

//@Preview(device = "spec:width=411dp,height=891dp", showBackground = true, showSystemUi = true)
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomePage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    newsViewModel: NewsViewModel
) {
    val authState = authViewModel.authState.observeAsState()
    val articles by newsViewModel.articles.observeAsState(emptyList())





//    LaunchedEffect(authState.value) {
//        when (authState.value) {
//            is AuthState.UnAuthenticated -> navController.navigate(Signup)
//            else -> Unit
//        }
//    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp, bottom = 16.dp)
    ) {
        CategoriesBar(newsViewModel)

            LazyColumn() {
                items(articles) { article ->
                    NewsCard(article)
                }
            }

//        TextButton(onClick = {
//            authViewModel.signout()
//        }) {
//            Text(text = "Sign out")
//        }
    }
}


@Composable
fun NewsCard(article: Article) {
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 8.dp)
            .height(220.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

            AsyncImage(
                model = article.urlToImage
                    ?: "https://png.pngtree.com/png-vector/20190820/ourmid/pngtree-no-image-vector-illustration-isolated-png-image_1694547.jpg",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
            )
            IconButton(
                onClick = {
                    ShareCompat
                        .IntentBuilder(context)
                        .setType("text/plain")
                        .setChooserTitle("Share article with: ")
                        .setText(article.url)
                        .startChooser()
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(12.dp)
                    .background(
                        Color.White.copy(alpha = 0.9f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share",
                    tint = Color(0xFF6A4C93)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomStart)
                    .height(100.dp)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.75f))
                        )
                    )
            )

            Text(
                text = article.title,
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
            )
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
