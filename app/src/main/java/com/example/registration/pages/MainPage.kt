package com.example.registration.pages

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PersonPin
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.registration.viewmodels.AuthViewModel
import com.example.registration.viewmodels.NavItem
import com.example.registration.viewmodels.NewsViewModel

@Composable
fun MainPage(
    modifier: Modifier = Modifier,
    navController: NavController,
    authViewModel: AuthViewModel,
    newsViewModel: NewsViewModel
) {
    val navItems = listOf(
        NavItem("Home", Icons.Default.Home),
        NavItem("Explore", Icons.Default.Explore),
        NavItem("Bookmark", Icons.Default.Bookmark),
        NavItem("Profile", Icons.Default.PersonPin)
    )

    var selectedItem by rememberSaveable { mutableIntStateOf(0) }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppTopBar()
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFFF8F9FC),
            ) {
                navItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItem == index,
                        onClick = { selectedItem = index },
                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.label
                            )
                        },
                        label = { Text(item.label) }
                    )
                }
            }
        }
    ) { innerPadding ->
        val contentModifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)

        when (selectedItem) {
            0 -> HomePage(
                modifier = contentModifier,
                navController = navController,
                authViewModel = authViewModel,
                newsViewModel = newsViewModel
            )

            1 -> ExplorePage(modifier = contentModifier)
            2 -> BookmarkPage(modifier = contentModifier)
            3 -> ProfilePage(modifier = contentModifier)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "NEWS",
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xFFF8F9FC),
            titleContentColor = Color(0xFF2878D0)
        )
    )
}
