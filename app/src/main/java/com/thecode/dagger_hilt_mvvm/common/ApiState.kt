package com.thecode.dagger_hilt_mvvm.common

sealed class ApiState {
    object Loading : ApiState()
    object Idle : ApiState()
    data class Error(val error: Throwable) : ApiState()
}
