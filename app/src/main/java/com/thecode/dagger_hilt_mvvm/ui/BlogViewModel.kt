package com.thecode.dagger_hilt_mvvm.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecode.dagger_hilt_mvvm.AppDispatchers
import com.thecode.dagger_hilt_mvvm.model.Blog
import com.thecode.dagger_hilt_mvvm.repository.BlogRepository
import com.thecode.dagger_hilt_mvvm.usecase.BlogUseCase
import com.thecode.dagger_hilt_mvvm.util.DataState
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
    private val _dataState: MutableLiveData<DataState<List<Blog>>> = MutableLiveData()

    val dataState: LiveData<DataState<List<Blog>>>
        get() = _dataState

    fun setStateEvent(mainStateEvent: MainStateEvent) {
        viewModelScope.launch(appDispatchers.default) {
            when (mainStateEvent) {
                is MainStateEvent.GetBlogEvents -> {
//                     blogUseCase.getBlog()
//                        .onEach { dataState ->
//                            _dataState.value = dataState
//                        }
//                        .launchIn(viewModelScope)

                    blogUseCase.getBlog().collect {
                        _dataState.postValue(it)
                    }
                }

                is MainStateEvent.None -> {
                    // No action
                }
            }
        }
    }
}

sealed class MainStateEvent {
    object GetBlogEvents : MainStateEvent()
    object None : MainStateEvent()
}
