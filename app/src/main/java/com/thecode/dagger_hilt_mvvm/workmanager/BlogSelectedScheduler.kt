package com.thecode.dagger_hilt_mvvm.workmanager

import android.content.Context
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.thecode.dagger_hilt_mvvm.AppDispatchers
import com.thecode.dagger_hilt_mvvm.database.BlogSelectedDao
import com.thecode.dagger_hilt_mvvm.database.BlogSelectedEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BlogSelectedScheduler @Inject constructor(
    @ApplicationContext val context: Context,
    private val blogSelectedDao: BlogSelectedDao,
    private val appDispatchers: AppDispatchers
) {

    fun init() {
        GlobalScope.launch(appDispatchers.default) {
            blogSelectedDao.get().collect { selectedBlogs ->
                selectedBlogs.forEach {
                    startWorkManager(context, it)
                }
            }
        }
    }

    private fun startWorkManager(
        context: Context,
        blogSelected: BlogSelectedEntity
    ) {
        if (blogSelected.date == null) {
            val data = Data.Builder()
                .putLong("id", blogSelected.id)
                .putString("title", blogSelected.title)
                .putString("body", blogSelected.body)
                .putString("image", blogSelected.image)
                .build()

            // IMPORTANT - Note the HiltWorkeFactory in MyApplication class
            // and ref to Hilt Worker in AndroidManifest.xml as these are needed
            // for the BlogSelectedWorker to be instantiated in Hilt.
            val request = OneTimeWorkRequestBuilder<BlogSelectedWorker>()
                .setInputData(data)
                .build()

            WorkManager.getInstance(context)
                .enqueue(request)
        }
    }
}
