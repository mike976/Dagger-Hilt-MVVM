package com.thecode.dagger_hilt_mvvm.database

import com.thecode.dagger_hilt_mvvm.model.BlogSelected
import com.thecode.dagger_hilt_mvvm.util.EntityMapper
import javax.inject.Inject

class BlogSelectedMapper @Inject constructor() : EntityMapper<BlogSelectedEntity, BlogSelected> {
    override fun mapFromEntity(entity: BlogSelectedEntity): BlogSelected {
        return BlogSelected(
            id = entity.id,
            title = entity.title,
            date = entity.date
        )
    }

    override fun mapToEntity(domainModel: BlogSelected): BlogSelectedEntity {
        return BlogSelectedEntity(
            id = domainModel.id,
            title = domainModel.title,
            date = domainModel.date
        )
    }

    fun mapFromEntityList(entities: List<BlogSelectedEntity>): List<BlogSelected> {
        return entities.map { mapFromEntity(it) }
    }
}
