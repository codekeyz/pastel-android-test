package com.codekeyz.newsfeed.ui.home

import android.app.Activity
import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.codekeyz.newsfeed.R
import com.codekeyz.newsfeed.model.Article

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeRoute(
    homeViewModel: HomeViewModel
) {
    // UiState of the HomeScreen
    val uiState by homeViewModel.uiState.collectAsStateWithLifecycle()

    HomeRoute(uiState = uiState, refresh = homeViewModel::refreshData)
}

@Composable
fun HomeRoute(
    uiState: HomeUiState,
    refresh: () -> Unit,
) {
    Scaffold(
        topBar = {
            if (uiState !is HomeUiState.Loading)
                TopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) }
                )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {

            when (uiState) {
                is HomeUiState.Loading -> ProgressView()
                is HomeUiState.Error -> ErrorView(errorMessage = uiState.errorMessage, refresh)
                is HomeUiState.Success -> SuccessView(uiState.articleFeed)
            }
        }

    }
}

@Composable
private fun ProgressView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(color = Color.White, strokeWidth = 1.dp)
        Text(text = "fetching data, please wait")
    }
}

@Composable
private fun ErrorView(errorMessage: String, retry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(10.dp, alignment = Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(text = errorMessage, textAlign = TextAlign.Center)
        Button(onClick = retry) {
            Text(text = "Refresh")
        }
    }
}

@Composable
private fun SuccessView(articleFeed: List<Article>, context: Context = LocalContext.current) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxSize()
    ) {
        itemsIndexed(articleFeed) { _, item ->
            ArticleListItem(
                article = item,
                onclick = { openCustomTab(item, context) })
        }
    }
}

fun openCustomTab(article: Article, context: Context) {
    // on below line we are creating a variable for
    // our URL which we have to open in chrome tabs
    val URL = article.url
    val package_name = "com.android.chrome"


    // on below line we are creating a variable
    // for the activity and initializing it.
    val activity = (context as? Activity)

    // on below line we are creating a variable for
    // our builder and initializing it with
    // custom tabs intent
    val builder = CustomTabsIntent.Builder()

    // on below line we are setting show title
    // to true to display the title for
    // our chrome tabs.
    builder.setShowTitle(false)


    // on below line we are enabling instant
    // app to open if it is available.
    builder.setInstantAppsEnabled(true)

    // on below line we are setting tool bar color for our custom chrome tabs.
    builder.setToolbarColor(ContextCompat.getColor(context, R.color.gunmetal))

    // on below line we are creating a
    // variable to build our builder.
    val customBuilder = builder.build()

    customBuilder.intent.setPackage(package_name)
    customBuilder.launchUrl(context, Uri.parse(URL))


//    // this method will be called if the
//    // chrome is not present in user device.
//    // in this case we are simply passing URL
//    // within intent to open it.
//    val i = Intent(Intent.ACTION_VIEW, Uri.parse(URL))

    // on below line we are calling start
    // activity to start the activity.
//    activity?.startActivity(customBuilder.intent)
}

