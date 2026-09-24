package com.example.registration.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.registration.network.Article
import com.example.registration.network.News
import com.example.registration.network.NewsCallable
import com.example.registration.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel : ViewModel() {

    private val _articles = MutableLiveData<List<Article>>()
    val articles: LiveData<List<Article>> = _articles
    val c: NewsCallable = RetrofitInstance.api

    init {
        loadNews()
    }

    fun loadNews(category: String = "general") {
        c.getNews(category = category).enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News?>, response: Response<News?>) {
                val news = response.body()
                _articles.value = news?.articles
            }

            override fun onFailure(call: Call<News?>, t: Throwable) {
                Log.d("trace", "err: ${t.message}")
            }
        })
    }

    fun searchNews(query: String) {
        c.getNewsSearch(query = query).enqueue(object : Callback<News> {
            override fun onResponse(
                call: Call<News?>, response: Response<News?>
            ) {
                val news = response.body()
                _articles.value = news?.articles
            }

            override fun onFailure(
                call: Call<News?>, t: Throwable
            ) {
                TODO("Not yet implemented")
            }
        })
    }
}