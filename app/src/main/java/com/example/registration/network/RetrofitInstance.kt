package com.example.registration.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit: Retrofit by lazy {
        Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val api: NewsCallable by lazy {
        retrofit.create(NewsCallable::class.java)
    }
}