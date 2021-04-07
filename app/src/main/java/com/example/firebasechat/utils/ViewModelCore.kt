package com.example.firebasechat.utils

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class ViewModelCore<E> : ViewModel(){

    val mutableLiveData = MutableLiveData<E>()
}