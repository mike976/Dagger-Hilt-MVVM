package com.thecode.dagger_hilt_mvvm.common.livedata

import androidx.lifecycle.LiveData

open class OneOffLiveData<T> : LiveData<OneOffEvent<T>>()
