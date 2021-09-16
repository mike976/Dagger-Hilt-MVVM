package com.thecode.dagger_hilt_mvvm.common

import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.ViewFlipper
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.IdRes
import androidx.annotation.DrawableRes
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.children
import com.bumptech.glide.Glide
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.thecode.dagger_hilt_mvvm.R

fun View.setCutCornerBackground(
    @ColorRes backgroundColorResId: Int = R.color.surface,
    @DimenRes radiusRes: Int = R.dimen.MarginXXXXLarge
) {
    val radius = context.resources.getDimension(radiusRes)
    val shapeAppearanceModel = ShapeAppearanceModel()
        .toBuilder()
        .setTopRightCorner(CornerFamily.CUT, radius)
        .build()
    val shapeDrawable = MaterialShapeDrawable(shapeAppearanceModel)
    shapeDrawable.setFillColor(
        ContextCompat.getColorStateList(
            context,
            backgroundColorResId
        )
    )
    ViewCompat.setBackground(this, shapeDrawable)
}

fun ViewFlipper.showViewIndex(index: Int) {
    when (index) {
        displayedChild, -1 -> return
        else -> displayedChild = index
    }
}

fun ViewFlipper.showView(view: View) {
    showViewIndex(indexOfChild(view))
}

fun ViewFlipper.showViewId(@IdRes id: Int) {
    showViewIndex(children.indexOfFirst { it.id == id })
}

fun ImageView.loadImage(@Nullable imageUrl: String?, @DrawableRes placeholderId: Int) {
    val imageUri = if (imageUrl.isNullOrBlank()) Uri.EMPTY else Uri.parse(imageUrl)

    Glide.with(this)
        .load(imageUri)
        .placeholder(placeholderId)
        .error(placeholderId)
        .fallback(placeholderId)
        .into(this)
}
