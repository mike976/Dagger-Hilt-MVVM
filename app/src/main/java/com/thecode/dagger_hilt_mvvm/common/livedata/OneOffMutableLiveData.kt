package com.thecode.dagger_hilt_mvvm.common.livedata

class OneOffMutableLiveData<T> : OneOffLiveData<T>() {

    fun postOneOff(value: T) = super.postValue(OneOffEvent(value))

    fun setOneOff(value: T) = super.setValue(OneOffEvent(value))
}
