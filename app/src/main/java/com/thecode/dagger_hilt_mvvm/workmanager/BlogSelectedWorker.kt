package com.thecode.dagger_hilt_mvvm.workmanager

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.thecode.dagger_hilt_mvvm.model.BlogSelected
import com.thecode.dagger_hilt_mvvm.repository.BlogRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.delay
import java.lang.Exception
import java.util.Date

@HiltWorker
class BlogSelectedWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
    private val blogRepository: BlogRepository
) : CoroutineWorker(context, params) {

    @Suppress("TooGenericExceptionCaught", "MagicNumber")
    override suspend fun doWork(): Result {

        val id = inputData.getLong("id", 0)
        val title = inputData.getString("title")!!
        val body = inputData.getString("body")!!
        val image = inputData.getString("image")!!

        val blogSelected = BlogSelected(
            id = id,
            title = title,
            date = Date(),
            body = body,
            image = image
        )

        return try {
            delay(1000L)
            blogRepository.insertBlogSelected(blogSelected)
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}
