package com.example.registration.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.registration.viewmodels.AuthViewModel
import com.example.registration.viewmodels.NewsViewModel
import com.example.registration.pages.HomePage
import com.example.registration.pages.LoginPage
import com.example.registration.pages.SignupPage


@Composable
fun MyNavigation (
    modifier: Modifier = Modifier,
    authViewModel: AuthViewModel,
    newsViewModel: NewsViewModel
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Home
    ){
        composable<Home> {
            HomePage(modifier, navController, authViewModel, newsViewModel)
        }

        composable<Login> {
            LoginPage(modifier, navController, authViewModel)
        }

        composable<Signup> {
            SignupPage(modifier, navController, authViewModel)
        }
    }

}