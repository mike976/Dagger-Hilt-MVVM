package com.thecode.dagger_hilt_mvvm.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thecode.dagger_hilt_mvvm.TestAppDispatchers
import com.thecode.dagger_hilt_mvvm.common.DataState
import com.thecode.dagger_hilt_mvvm.common.livedata.OneOffEvent
import com.thecode.dagger_hilt_mvvm.emptyBlogModel
import com.thecode.dagger_hilt_mvvm.model.Blog
import com.thecode.dagger_hilt_mvvm.ui.blogs.BlogViewModel
import com.thecode.dagger_hilt_mvvm.usecase.BlogUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BlogViewModelTest {

//    @get:Rule
//    var mainCoroutineRule = MainCoroutineRule()

    private val appDispatchers = TestAppDispatchers()

    private val defaultDispatcher = TestAppDispatchers().default

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    lateinit var classUnderTest: BlogViewModel

    @MockK
    private lateinit var blogUseCase: BlogUseCase

    private val blogsFlow = MutableStateFlow<DataState<List<Blog>>>(DataState.CompleteWithContent(listOf(
        emptyBlogModel()
    )))

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        Dispatchers.setMain(defaultDispatcher)

        coEvery { blogUseCase.getBlog() } answers { blogsFlow }

        classUnderTest = BlogViewModel(blogUseCase, appDispatchers)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

    @Test
    fun `when blogs is requested then get blogs`() {

        // When
        classUnderTest.refreshBlogs()

        // Then
        coVerify { blogUseCase.getBlog() }
    }

    @Test
    fun `when new blogs has been added then the view state will contain the new blogs`() =
        runBlockingTest {

        // Given
        var newBlog = emptyBlogModel().copy(
            id = 1,
            title = "new Blog"
        )

        // When
        classUnderTest.refreshBlogs()
        blogsFlow.emit(DataState.CompleteWithContent(listOf(newBlog)))

        // Then
        (classUnderTest.viewModelState.value as BlogViewModel.State.ReceivedBlogs?)?.apply {
            assertEquals(1, blogs.size)
            assertEquals(newBlog.id, blogs[0].id)
            assertEquals(newBlog.title, blogs[0].title)
        }
    }

    @Test
    fun `when navigateTo button is pressed then navigate to blank page`() {

        // When
        classUnderTest.onNavigateToButtonPressed()

        // Then
        assertEquals(OneOffEvent(BlogViewModel.NavigationEvent.GotoBlankPage), classUnderTest.navigationEvent.value)
    }
}
