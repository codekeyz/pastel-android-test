package com.codekeyz.newsfeed.data.article.impl

import com.codekeyz.newsfeed.data.article.ApiService
import com.codekeyz.newsfeed.data.article.ArticleRepository
import com.codekeyz.newsfeed.model.Article
import com.codekeyz.newsfeed.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ArticleRepositoryimpl(private val apiService: ApiService) : ArticleRepository {
    override suspend fun fetchArticles(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                val data = apiService.fetchNews(apiKey = "2d021085c2e64c23927ff485d9f4299b")
                Result.Success(data.articles)
            } catch (e: Exception) {
                Result.Error(e)
            }
        }
    }


}