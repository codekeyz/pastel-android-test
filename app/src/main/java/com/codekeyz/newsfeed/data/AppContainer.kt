package com.codekeyz.newsfeed.data

import android.content.Context
import com.codekeyz.newsfeed.data.article.ApiService
import com.codekeyz.newsfeed.data.article.ArticleRepository
import com.codekeyz.newsfeed.data.article.impl.ArticleRepositoryimpl

interface AppContainer {
    val articleRepostory: ArticleRepository
}

class AppContainerImpl(
    private val applicationContext: Context,
) : AppContainer {
    override val articleRepostory: ArticleRepository by lazy {
        val apiService = ApiService.getInstance()

        ArticleRepositoryimpl(apiService)
    }
}