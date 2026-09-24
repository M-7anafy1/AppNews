package com.example.registration

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.registration.navigation.MyNavigation
import com.example.registration.viewmodels.AuthViewModel
import com.example.registration.viewmodels.NewsViewModel
import com.example.registration.ui.theme.RegistrationTheme

class MainActivity : ComponentActivity() {

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            RegistrationTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    val authViewModel: AuthViewModel = viewModel()
                    val newsViewModel: NewsViewModel = viewModel()
                    MyNavigation(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel,
                        newsViewModel = newsViewModel
                    )
                }
            }
        }
    }
}


