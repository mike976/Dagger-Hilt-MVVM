package com.thecode.dagger_hilt_mvvm.ui.selectedblogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecode.dagger_hilt_mvvm.AppDispatchers
import com.thecode.dagger_hilt_mvvm.model.BlogSelected
import com.thecode.dagger_hilt_mvvm.repository.BlogRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedBlogsViewModel @Inject constructor(
    private val repository: BlogRepository,
    private val appDispatchers: AppDispatchers
) : ViewModel() {

    private val viewState: MutableLiveData<State> = MutableLiveData()
    val viewModelState: LiveData<State> = viewState

    init {
        viewModelScope.launch(appDispatchers.default) {
            repository.getBlogsSelected().firstOrNull {
                when {
                    it.isNullOrEmpty().not() -> {
                        viewState.postValue(State.ReceivedSelectedBlogs(it))
                        true
                    }
                    else -> {
                        viewState.postValue(State.NoSelectedBlogs)
                        false
                    }
                }
            }
        }
    }

    sealed class State {
        data class ReceivedSelectedBlogs(val blogs: List<BlogSelected>) : State()
        object NoSelectedBlogs : State()
    }
}
