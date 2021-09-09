package com.thecode.dagger_hilt_mvvm.usecase

import com.thecode.dagger_hilt_mvvm.MainCoroutineRule
import com.thecode.dagger_hilt_mvvm.common.DataState
import com.thecode.dagger_hilt_mvvm.emptyBlogModel
import com.thecode.dagger_hilt_mvvm.model.Blog
import com.thecode.dagger_hilt_mvvm.repository.BlogRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BlogUseCaseTest {

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var classUnderTest: BlogUseCase

    // Dependencies
    @MockK
    lateinit var blogRepository: BlogRepository

    private val blogsFlow = MutableStateFlow<DataState<List<Blog>>>(
        DataState.CompleteWithContent(listOf(
        emptyBlogModel()
    )))

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { blogRepository.getBlog() } answers { blogsFlow }

        classUnderTest = BlogUseCase(blogRepository)
    }

    @Test
    fun `when state is received then it is sent on`() = runBlockingTest {
        // Given
        val data = mockk<DataState<List<Blog>>>()

        // When
        blogsFlow.emit(data)

        // Then
        Assert.assertEquals(data, classUnderTest.getBlog().first())
    }
}
