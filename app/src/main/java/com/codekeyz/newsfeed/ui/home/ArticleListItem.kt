package com.codekeyz.newsfeed.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.codekeyz.newsfeed.model.Article

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ArticleListItem(
    onclick: () -> Unit,
    article: Article
) {

    Box(modifier = Modifier.height(200.dp).clickable { onclick() }) {

        Box(
            modifier = Modifier
                .background(color = Color.Black)
                .alpha(0.3f)
        ) {
            GlideImage(
                model = article.urlToImage,
                contentDescription = article.title,
                contentScale = ContentScale.FillWidth
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = article.title,
                color = Color.White,
                style = MaterialTheme.typography.h6,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            if (article.author != null && article.author.isNotBlank()) {
                Text(text = article.author, maxLines = 1, color = Color.LightGray, fontSize = 14.sp)
            }
        }
    }
}