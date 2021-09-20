package com.thecode.dagger_hilt_mvvm.ui.selectedblogs.model

import com.thecode.dagger_hilt_mvvm.model.BlogSelected
import com.thecode.dagger_hilt_mvvm.util.UiModelMapper
import javax.inject.Inject

class SelectedBlogUiModelMapper @Inject constructor() : UiModelMapper<BlogSelected, SelectedBlogUiModel> {

    override fun mapFromDomainModel(domainModel: BlogSelected, isPressed: Boolean): SelectedBlogUiModel =
        SelectedBlogUiModel(
            id = domainModel.id,
            title = domainModel.title,
            body = domainModel.body,
            image = domainModel.image,
            date = domainModel.date,
            isSelected = isPressed
        )

    override fun mapFromUiModel(uiModel: SelectedBlogUiModel): BlogSelected =
        BlogSelected(
            id = uiModel.id,
            title = uiModel.title,
            body = uiModel.body,
            image = uiModel.image,
            date = uiModel.date
        )

    fun mapFromDomainModelList(
        domainModels: List<BlogSelected>,
        blogPressed: SelectedBlogUiModel?
    ): List<SelectedBlogUiModel> {
        return domainModels.map {
            mapFromDomainModel(it, blogPressed?.title == it.title)
        }
    }
}
