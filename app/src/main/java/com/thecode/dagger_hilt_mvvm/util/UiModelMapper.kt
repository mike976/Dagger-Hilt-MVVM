package com.thecode.dagger_hilt_mvvm.util

interface UiModelMapper<DomainModel, UiModel> {

    fun mapFromDomainModel(domainModel: DomainModel, isPressed: Boolean): UiModel

    fun mapFromUiModel(uiModel: UiModel): DomainModel
}
