package com.codekeyz.newsfeed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.codekeyz.newsfeed.ui.home.HomeRoute
import com.codekeyz.newsfeed.ui.home.HomeViewModel
import com.codekeyz.newsfeed.ui.theme.NewsFeedTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = (application as NewsfeedApp).container

        setContent {

            val homeViewModel: HomeViewModel = viewModel(
                factory = HomeViewModel.provideFactory(appContainer.articleRepostory)
            )

            NewsFeedTheme {
                HomeRoute(homeViewModel = homeViewModel)
            }
        }
    }
}




