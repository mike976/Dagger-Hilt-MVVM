package com.thecode.dagger_hilt_mvvm.ui.selectedblogs.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thecode.dagger_hilt_mvvm.common.compose.LoadComposableImage
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.model.SelectedBlogUiModel
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.viewmodel.SelectedBlogsViewModel

@Composable
fun ShowSelectedBlogsScreen(
    viewState: SelectedBlogsViewModel.State.ReceivedSelectedBlogs,
    selectedItemPressedCallback: (SelectedBlogUiModel) -> (Unit)
) {
    Column {
        Row(
            modifier = Modifier
                .background(color = Color.Blue)
                .fillMaxWidth()
        ) {
            ScreenHeader("Jetpack Compose List")
        }
        Row() {
            BlogList(viewState.blogs, selectedItemPressedCallback)
        }
    }
}

@Composable
fun ScreenHeader(
    header: String
) {
    Text(text = header,
        style = MaterialTheme.typography.h6,
        color = Color.White,
        modifier = Modifier.padding(16.dp)
    )
}

@Composable
fun BlogList(
    blogList: List<SelectedBlogUiModel>,
    selectedItemPressedCallback: (SelectedBlogUiModel) -> (Unit)
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 0.dp),
    ) {
        items(
            items = blogList,
            itemContent = { blog ->
                BlogListItem(blog,
                    selectedItemPressedCallback
                )
            }
        )
    }
}

@Composable
fun BlogListItem(
    blog: SelectedBlogUiModel,
    selectedItemPressedCallback: (SelectedBlogUiModel) -> (Unit)
) {
    val itemPressedColor =
        if (blog.isSelected) Color.LightGray else Color.White

    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { selectedItemPressedCallback(blog) },
        elevation = 2.dp,

        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Row(
            Modifier.background(color = itemPressedColor)
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                LoadComposableImage(blog.image, 80.dp)
            }
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
                    .background(color = itemPressedColor)
            ) {
                Text(text = blog.title, style = MaterialTheme.typography.h6)
                Text(text = blog.body, style = MaterialTheme.typography.subtitle2)
            }
        }
    }
}
