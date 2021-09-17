package com.thecode.dagger_hilt_mvvm.ui.selectedblogs.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.thecode.dagger_hilt_mvvm.common.compose.LoadComposableImage
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.SelectedBlogsViewModel

@Composable
fun ShowSelectedBlogsScreen(
    viewState: SelectedBlogsViewModel.State.ReceivedSelectedBlogs
) {
    val blogsList = remember { viewState.blogs }
    Column{

        Row() {
            Text(text = "Jetpack Compose List", style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(16.dp))
        }
        Row() {
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
            ) {
                items(
                    items = blogsList,
                    itemContent = { blog ->
                        BlogListItem(
                            header = blog.title,
                            detail = blog.body,
                            imageUrl = blog.image
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun BlogListItem(
    header: String,
    detail: String,
    imageUrl: String
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = Color.White,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                LoadComposableImage(imageUrl, 80.dp)
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = header, style = MaterialTheme.typography.h6)
                Text(text = detail, style = MaterialTheme.typography.subtitle2)
            }
        }
    }
}
