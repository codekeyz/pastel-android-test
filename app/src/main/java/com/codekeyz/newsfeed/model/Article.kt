package com.codekeyz.newsfeed.model

data class Article (
    val author: String?,
    val title: String,
    val url: String,
    val urlToImage: String,
)

data class ArticleResponse (
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)