package com.codekeyz.newsfeed.data.article

import com.codekeyz.newsfeed.model.ArticleResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines")
    suspend fun fetchNews(
        @Query("country") country: String = "us",
        @Query("apiKey") apiKey: String
    ): ArticleResponse

    companion object {
        var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null) {
                apiService = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/v2/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}