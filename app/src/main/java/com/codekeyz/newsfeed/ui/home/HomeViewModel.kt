package com.codekeyz.newsfeed.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.codekeyz.newsfeed.data.article.ArticleRepository
import com.codekeyz.newsfeed.model.Article
import com.codekeyz.newsfeed.model.Result
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

sealed interface HomeUiState {
    val isLoading: Boolean
    val errorMessage: String

    data class NoNews(
        override val isLoading: Boolean,
        override val errorMessage: String,
    ) : HomeUiState

    data class HasNews(
        val articleFeed: List<Article>,
        override val isLoading: Boolean,
        override val errorMessage: String,
    ) : HomeUiState
}

private data class HomeViewModelState(
    val articleFeed: List<Article>? = null,
    val isLoading: Boolean = false,
    val errorMessage: String = "",
) {

    fun toUiState(): HomeUiState =
        if (articleFeed == null) {
            HomeUiState.NoNews(isLoading = isLoading, errorMessage = errorMessage)
        } else {
            HomeUiState.HasNews(
                articleFeed = articleFeed,
                isLoading = isLoading,
                errorMessage = errorMessage,
            )
        }

}

class HomeViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    private val viewModelState = MutableStateFlow(HomeViewModelState(isLoading = true))

    // UI state exposed to the UI
    val uiState = viewModelState
        .map(HomeViewModelState::toUiState)
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            viewModelState.value.toUiState()
        )

    init {
        refreshData()
    }

    /**
     * Refresh articles and update the UI state accordingly
     */
    fun refreshData() {
        // Ui state is refreshing
        viewModelState
            .update { it.copy(isLoading = true) }

        viewModelScope.launch {
            val result = articleRepository.fetchArticles()

            viewModelState.update {
                when (result) {
                    is Result.Success -> it.copy(articleFeed = result.data, isLoading = false)
                    is Result.Error ->
                        it.copy(
                            errorMessage = "An error occurred while fetching data. Check your internet connection",
                            isLoading = false
                        )

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

