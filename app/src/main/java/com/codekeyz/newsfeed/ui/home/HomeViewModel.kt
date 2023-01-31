package com.codekeyz.newsfeed.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.codekeyz.newsfeed.data.article.ArticleRepository
import com.codekeyz.newsfeed.model.Article
import com.codekeyz.newsfeed.model.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed class HomeUiState {
    object Loading : HomeUiState()
    data class Success(val articleFeed: List<Article>) : HomeUiState()
    data class Error(val errorMessage: String) : HomeUiState()
}

class HomeViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val viewModelState = MutableStateFlow<HomeUiState>(HomeUiState.Loading)

    // UI state exposed to the UI
    val uiState = viewModelState.asStateFlow()

    init {
        refreshData()
    }

    /**
     * Refresh articles and update the UI state accordingly
     */
    fun refreshData() {
        viewModelScope.launch {
            val result = articleRepository.fetchArticles()

            viewModelState.update {
                when (result) {
                    is Result.Success -> HomeUiState.Success(result.data)
                    is Result.Error -> HomeUiState.Error("An error occurred while fetching data. Check your internet connection")
                }
            }
        }
    }

    /**
     * Factory for HomeViewModel that takes ArticleRepository as a dependency
     */
    companion object {
        fun provideFactory(
            articleRepository: ArticleRepository,
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HomeViewModel(articleRepository) as T
            }
        }
    }

}

