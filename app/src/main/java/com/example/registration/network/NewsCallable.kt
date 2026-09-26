package com.example.registration.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsCallable {
    //https://newsapi.org/v2/everything?q=bitcoin&apiKey=4d924bb6263a40d09d51836fee240e9a
    //https://newsapi.org/v2/top-headlines?country=de&category=business&apiKey=4d924bb6263a40d09d51836fee240e9a
    @GET("v2/everything?q=bitcoin&apiKey=4d924bb6263a40d09d51836fee240e9a")
    fun getNews(
//        @Query("country") country: String = "us",
//        @Query("category") category: String = "general",
//        @Query("pageSize") pageSize: Int = 30,
//        @Query("apiKey") apiKey: String = "4d924bb6263a40d09d51836fee240e9a"
    ): Call<News>
    
    @GET("v2/everything")
    fun getNewsSearch(
        @Query("q") query: String ,
        @Query("apiKey") apiKey: String = "4d924bb6263a40d09d51836fee240e9a"
    ): Call<News>
}