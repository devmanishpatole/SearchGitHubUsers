package com.devmanishpatole.githubusers.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devmanishpatole.githubusers.base.BaseViewModel

abstract class BaseItemViewModel<T> : BaseViewModel() {

    private val _data = MutableLiveData<T>()
    val data: LiveData<T>
        get() = _data


    open fun updateData(data: T) {
        _data.postValue(data)
    }

    fun onManualClear() = onCleared()
}