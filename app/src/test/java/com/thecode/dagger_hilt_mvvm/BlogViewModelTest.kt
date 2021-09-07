package com.thecode.dagger_hilt_mvvm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.thecode.dagger_hilt_mvvm.model.Blog
import com.thecode.dagger_hilt_mvvm.repository.MainRepository
import com.thecode.dagger_hilt_mvvm.ui.MainStateEvent
import com.thecode.dagger_hilt_mvvm.ui.BlogViewModel
import com.thecode.dagger_hilt_mvvm.util.DataState
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import junit.framework.Assert
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
    private lateinit var mainRepository: MainRepository

    private val blogsFlow = MutableStateFlow<DataState<List<Blog>>>(DataState.Success(listOf(
        emptyBlogModel())))

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        Dispatchers.setMain(defaultDispatcher)

        coEvery { mainRepository.getBlog() } answers { blogsFlow }

        classUnderTest = BlogViewModel(mainRepository, appDispatchers)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }

    @Test
    fun `when blogs is requested then get blogs`() {

        // When
        classUnderTest.setStateEvent(MainStateEvent.GetBlogEvents)

        // Then
        coVerify { mainRepository.getBlog() }
    }

    @Test
    fun `when new blogs has been added then the view state will contain the new blogs`() =
        runBlockingTest {

        // Given
        var blogs = listOf(emptyBlogModel().copy(
            id = 1,
            title = "new Blog"
        ))

        // When
        classUnderTest.setStateEvent(MainStateEvent.GetBlogEvents)
        blogsFlow.emit(DataState.Success(blogs))

        // Then
        Assert.assertEquals(DataState.Success(blogs), classUnderTest.dataState.value)
    }
}
