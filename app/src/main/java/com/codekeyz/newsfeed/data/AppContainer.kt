package com.codekeyz.newsfeed.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.codekeyz.newsfeed.data.article.ApiService
import com.codekeyz.newsfeed.data.article.ArticleRepository
import com.codekeyz.newsfeed.data.article.impl.ArticleCacheImpl
import com.codekeyz.newsfeed.data.article.impl.ArticleRepositoryimpl

interface AppContainer {
    val articleRepostory: ArticleRepository
}

class AppContainerImpl(
    private val context: Context,
) : AppContainer {


    override val articleRepostory: ArticleRepository by lazy {
        val apiService = ApiService.getInstance()
        val articleCache = ArticleCacheImpl(context)

        ArticleRepositoryimpl(apiService, articleCache)
    }
}