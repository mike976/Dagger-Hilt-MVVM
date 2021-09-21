package com.thecode.dagger_hilt_mvvm.ui.selectedblogs.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thecode.dagger_hilt_mvvm.AppDispatchers
import com.thecode.dagger_hilt_mvvm.repository.BlogRepository
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.model.SelectedBlogUiModel
import com.thecode.dagger_hilt_mvvm.ui.selectedblogs.model.SelectedBlogUiModelMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectedBlogsViewModel @Inject constructor(
    private val repository: BlogRepository,
    private val appDispatchers: AppDispatchers,
    private val uiModelMapper: SelectedBlogUiModelMapper
) : ViewModel() {

    private val state: MutableLiveData<State> = MutableLiveData()
    val viewState: LiveData<State> = state

    init {
        viewModelScope.launch(appDispatchers.default) {

            // simulate loading delay
            state.postValue(State.Loading)
            delay(2500L)

            repository.getBlogsSelected().firstOrNull {
                when {
                    it.isNullOrEmpty().not() -> {
                        state.postValue(
                            State.LoadedWithContent(
                                uiModelMapper.mapFromDomainModelList(it, null)
                            )
                        )
                        true
                    }
                    else -> {
                        state.postValue(State.LoadedWithNoContent)
                        false
                    }
                }
            }
        }
    }

    fun onBlogItemPressed(item: SelectedBlogUiModel) {
        viewModelScope.launch(appDispatchers.default) {
            repository.getBlogsSelected().firstOrNull()?.let {
                state.postValue(
                    State.LoadedWithContent(
                        uiModelMapper.mapFromDomainModelList(it, item)
                    )
                )
            }
        }
    }

    sealed class State {
        data class LoadedWithContent(val blogs: List<SelectedBlogUiModel>) : State()
        object LoadedWithNoContent : State()
        object Loading: State()
    }
}
