package com.deventium.livestate.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.deventium.livestate.event.LiveEvent

@Suppress("unused")
fun <T> LifecycleOwner.observe(liveEvent: LiveEvent<T>, observer: (T) -> Unit){
    liveEvent.observe(this, Observer(observer))
}

@Suppress("unused")
fun <T> LifecycleOwner.observe(liveData: LiveData<T>, observer: (T) -> Unit){
    liveData.observe(this, Observer(observer))
}