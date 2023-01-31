package com.codekeyz.newsfeed.data.article.impl

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codekeyz.newsfeed.data.article.ApiService
import com.codekeyz.newsfeed.data.article.ArticleCache
import com.codekeyz.newsfeed.data.article.ArticleRepository
import com.codekeyz.newsfeed.model.Article
import com.codekeyz.newsfeed.model.Result
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "articles")

class ArticleCacheImpl(private val context: Context) : ArticleCache {

    private val gson = GsonBuilder().create()

    companion object {
        val ARTICLES_KEY = stringPreferencesKey("articles_key")
    }

    override suspend fun get(): List<Article> {
        return try {
            val prefs = context.dataStore.data.first()
            val data = prefs[ARTICLES_KEY] ?: ""
            if (data.isEmpty()) return emptyList()

            val itemType = object : TypeToken<List<Article>>() {}.type
            gson.fromJson(data, itemType)

        } catch (e: Exception) {
            return emptyList()
        }
    }

    override suspend fun put(articles: List<Article>) {
        val json = gson.toJson(articles)
        context.dataStore.edit { it[ARTICLES_KEY] = json }
    }

}

class ArticleRepositoryimpl(
    private val apiService: ApiService,
    private val cache: ArticleCache,
) : ArticleRepository {

    override suspend fun fetchArticles(): Result<List<Article>> {
        return withContext(Dispatchers.IO) {
            try {
                val data = apiService.fetchNews(apiKey = "2d021085c2e64c23927ff485d9f4299b")
                cache.put(data.articles)

                Result.Success(data.articles)
            } catch (e: Exception) {
                val articles = cache.get()
                if (articles.isNotEmpty()) {
                    return@withContext Result.Success(articles)
                }

                Result.Error(e)
            }
        }
    }
}