package com.thecode.dagger_hilt_mvvm.ui.selectedblogs.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.thecode.dagger_hilt_mvvm.R
import com.thecode.dagger_hilt_mvvm.common.compose.LoadComposableImage
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.model.SelectedBlogUiModel
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.screens.previews.emptySelectedBlogUiModel
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.viewmodel.SelectedBlogsViewModel


// region Composables

@Composable
fun SelectedBlogsScreen(
    header: String,
    viewState: SelectedBlogsViewModel.State,
    selectedItemPressedCallback: (SelectedBlogUiModel) -> (Unit)
) {
    Column {
        Row(
            modifier = Modifier
                .background(color = Color.Blue)
                .fillMaxWidth()
        ) {
            HeaderView(header)
        }
        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment =  if (viewState is SelectedBlogsViewModel.State.LoadedWithContent) Alignment.Top else Alignment.CenterVertically
        ) {
            when (viewState) {
                is SelectedBlogsViewModel.State.Loading -> {
                    LoadingContentView()
                }
                is SelectedBlogsViewModel.State.LoadedWithContent -> {
                    ContentView(viewState.blogs, selectedItemPressedCallback)
                }
                is SelectedBlogsViewModel.State.LoadedWithNoContent -> {
                    NoContentView()
                }
            }
        }
    }
}

@Composable
fun ContentView(
    data: List<SelectedBlogUiModel>,
    selectedItemPressedCallback: (SelectedBlogUiModel) -> (Unit)
) {
    BlogList(data, selectedItemPressedCallback)
}

@Composable
fun NoContentView() {
    Text(text = "No data",
        style = MaterialTheme.typography.h6,
        color = Color.Blue,
        modifier = Modifier
            .padding(16.dp)
    )
}

@Composable
fun LoadingContentView() {
    CircularProgressIndicator(
        color = Color.Blue
    )
}

@Composable
fun HeaderView(
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
                    .width(80.dp)
                    .height(80.dp)
                    .clip(RoundedCornerShape(4.dp))
            ) {
                if (blog.image.isNotEmpty()) {
                    LoadComposableImage(blog.image, 80.dp)
                } else {
                    Image(
                        alignment = Alignment.Center,
                        painter = painterResource(R.drawable.placeholder),
                        contentDescription = null, // decorative
                        modifier = Modifier
                            .size(80.dp, 80.dp)
                    )
                }
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

// endregion

// region Previews
@Preview(
    "Screen with Content Preview",
    group = "Group 1"
)
@Composable
fun PreviewScreenWithContent() {
    val blog1 = createBlogUiModel(
        id = 1,
        title = "blog header 1",
        body = "blog detail 1",
        isSelected = false
    )

    val blog2 = createBlogUiModel(
        id = 2,
        title = "blog header 2",
        body = "blog detail 2",
        isSelected = false
    )

    val viewState = SelectedBlogsViewModel.State.LoadedWithContent(
        listOf(blog1, blog2)
    )
    SelectedBlogsScreen(
        header = "Header",
        viewState = viewState,
        selectedItemPressedCallback = { (blog) -> { } }
    )
}

@Preview(
    name = "Screen with No Content Preview",
    group = "Group 1"
)
@Composable
fun PreviewScreenWithNoContent() {
    val viewState = SelectedBlogsViewModel.State.LoadedWithNoContent
    SelectedBlogsScreen(
        header = "Header",
        viewState = viewState,
        selectedItemPressedCallback = { (blog) -> { } }
    )
}

@Preview(
    name = "Screen with Loading Content Preview",
    group = "Group 1"
)
@Composable
fun PreviewScreenWithLoadingContent() {
    val viewState = SelectedBlogsViewModel.State.Loading
    SelectedBlogsScreen(
        header = "Header",
        viewState = viewState,
        selectedItemPressedCallback = { (blog) -> { } }
    )
}

@Preview(
    name = "No Content Preview",
    group = "Group 1"
)
@Composable
fun PreviewNoContentView() {
    NoContentView()
}

@Preview(
    name = "Loading Content Preview",
    group = "Group 1"
)
@Composable
fun LoadingContentPreview(){
    LoadingContentView()
}

@Preview(
    name ="Header Preview",
    group = "Group 1",
)
@Composable
fun PreviewScreenHeader(){
    HeaderView(header = "Screen Header")
}

@Preview(
    name ="List Preview",
    group = "Group 1",
)
@Composable
fun PreviewList() {
    val blog1 = createBlogUiModel(
        id = 1,
        title = "blog header 1",
        body = "blog header 1",
        isSelected = false
    )

    val blog2 = createBlogUiModel(
        id = 2,
        title = "blog header 2",
        body = "blog header 2",
        isSelected = false
    )

    BlogList(
        listOf(blog1, blog2),
        selectedItemPressedCallback = { (blog1) -> { } }
    )
}

@Preview(
    name ="List Item Preview",
    group = "Group 1",
)
@Composable
fun PreviewListItem() {
    val blog = createBlogUiModel(
        id = 1,
        title = "blog header 1",
        body = "blog detail 1",
        isSelected = false
    )

    BlogListItem(
        blog,
        selectedItemPressedCallback = { (blog) -> { } }
    )
}

@Preview(
    name ="List Item Tapped Preview",
    group = "Group 1",
)
@Composable
fun PreviewTappedListItem() {
    val blog = createBlogUiModel(
        id = 1,
        title = "blog header 1",
        body = "blog detail 1",
        isSelected = true
    )

    BlogListItem(
        blog,
        selectedItemPressedCallback = { (blog) -> { } }
    )
}

private fun createBlogUiModel(
    id: Long,
    title: String,
    body: String,
    isSelected: Boolean
)  = emptySelectedBlogUiModel().copy(
    id = id,
    title = title,
    body = body,
    isSelected = isSelected,
    date = null
)

// endregion
