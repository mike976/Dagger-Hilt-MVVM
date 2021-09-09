package com.thecode.dagger_hilt_mvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecode.dagger_hilt_mvvm.AppDispatchers
import com.thecode.dagger_hilt_mvvm.common.DataState
import com.thecode.dagger_hilt_mvvm.common.livedata.OneOffMutableLiveData
import com.thecode.dagger_hilt_mvvm.model.Blog
import com.thecode.dagger_hilt_mvvm.usecase.BlogUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BlogViewModel
@Inject constructor(
    private val blogUseCase: BlogUseCase,
    private val appDispatchers: AppDispatchers
) : ViewModel() {

    private val viewState: MutableLiveData<State> = MutableLiveData()
    val viewModelState: LiveData<State> = viewState

    val navigationEvent: OneOffMutableLiveData<NavigationEvent> = OneOffMutableLiveData()

    init {
        viewModelScope.launch(appDispatchers.default) {
            blogUseCase.getBlog().collect { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        viewState.postValue(State.LoadingBlogs)
                    }
                    is DataState.ErrorWithContent -> {
                        viewState.postValue(
                            State.ReceivedBlogsOffline(dataState.data,
                                "no network - running offline"))
                    }
                    is DataState.CompleteWithContent -> {
                        viewState.postValue(State.ReceivedBlogs(dataState.data))
                    }
                }
            }
        }
    }

    fun refreshBlogs() {
        viewModelScope.launch(appDispatchers.default) {
            blogUseCase.refreshBlogs()
        }
    }

    fun onNavigateToButtonPressed() {
        navigationEvent.postOneOff(NavigationEvent.GotoBlankPage)
    }

    sealed class State {
        object LoadingBlogs : State()
        data class ReceivedBlogsOffline(val blogs: List<Blog>, val errorMsg: String) : State()
        data class ReceivedBlogs(val blogs: List<Blog>) : State()
    }

    sealed class NavigationEvent {
        object GotoBlankPage : NavigationEvent()
    }
}
