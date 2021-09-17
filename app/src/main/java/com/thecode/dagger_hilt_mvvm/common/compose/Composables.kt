package com.thecode.dagger_hilt_mvvm.common.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.Dp
import com.skydoves.landscapist.glide.GlideImage
import com.thecode.dagger_hilt_mvvm.R

@Composable
fun LoadComposableImage(imageUrl: String, size: Dp) {
    GlideImage(
        imageModel = imageUrl,
        contentScale = ContentScale.Crop,
        placeHolder = ImageBitmap.imageResource(R.drawable.placeholder),
        error = ImageBitmap.imageResource(R.drawable.placeholder),
        modifier = Modifier
            .size(size)
            .fillMaxWidth()
    )
}
