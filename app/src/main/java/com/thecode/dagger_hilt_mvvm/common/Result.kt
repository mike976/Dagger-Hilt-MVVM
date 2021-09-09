package com.thecode.dagger_hilt_mvvm.common

/**
 *  Kotlin Result is unstable, and, at the time of writing, not working for testing
 *  https://stackoverflow.com/questions/65420765/problems-with-kotlin-resultt-on-unit-tests
 *  https://youtrack.jetbrains.com/issue/KT-41163
 */
sealed class Result<T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure<T>(val error: Throwable) : Result<T>()
}

inline fun <R, T> Result<T>.fold(
    onSuccess: (value: T) -> R,
    onFailure: (exception: Throwable) -> R
): R = when (this) {
    is Result.Success -> onSuccess(value)
    is Result.Failure -> onFailure(error)
}

inline fun <R, T> Result<T>.map(
    transform: (T) -> R
): Result<R> = when (this) {
    is Result.Success -> Result.Success(transform(value))
    is Result.Failure -> Result.Failure(error)
}

inline fun <R, T> Result<T>.flatMap(
    transform: (T) -> Result<R>
): Result<R> = when (this) {
    is Result.Success -> transform(value)
    is Result.Failure -> Result.Failure(error)
}

inline fun <T> Result<T>.onSuccess(
    onSuccess: (value: T) -> Unit
): Result<T> {
    if (this is Result.Success) onSuccess(value)
    return this
}

inline fun <T> Result<T>.onFailure(
    onFailure: (exception: Throwable) -> Unit
): Result<T> {
    if (this is Result.Failure) onFailure(error)
    return this
}

@Suppress("TooGenericExceptionCaught")
inline fun <T, R> T.runCatching(block: T.() -> R): Result<R> {
    return try {
        Result.Success(block())
    } catch (e: Throwable) {
        Result.Failure(e)
    }
}
