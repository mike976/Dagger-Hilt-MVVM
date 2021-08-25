package com.thecode.dagger_hilt_mvvm

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineDispatcher

class TestAppDispatchers(
    val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : AppDispatchers {
    override val default: CoroutineDispatcher = dispatcher
    override val io: CoroutineDispatcher = dispatcher
    override val main: CoroutineDispatcher = dispatcher
}
