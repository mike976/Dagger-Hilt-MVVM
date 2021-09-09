package com.thecode.dagger_hilt_mvvm.common

sealed class DataState<out T : Any> {
    object Loading : DataState<Nothing>()
    data class LoadingWithContent<out T : Any>(val data: T) : DataState<T>()
    data class CompleteWithContent<out T : Any>(val data: T) : DataState<T>()
    object CompleteWithEmpty : DataState<Nothing>()
    data class ErrorWithContent<out T : Any>(val data: T, val error: Throwable) : DataState<T>()
    data class Error(val error: Throwable) : DataState<Nothing>()
}
