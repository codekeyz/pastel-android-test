package com.codekeyz.newsfeed.data.article

import com.codekeyz.newsfeed.model.Article
import com.codekeyz.newsfeed.model.Result

interface ArticleRepository {
    suspend fun fetchArticles(): Result<List<Article>>
}

interface ArticleCache {
    suspend fun get(): List<Article>
    suspend fun put(articles: List<Article>)
}