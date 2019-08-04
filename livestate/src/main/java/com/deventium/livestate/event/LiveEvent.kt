package com.deventium.livestate.event

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer

class LiveEvent<T> internal constructor() {

    private val liveData = EventLiveData<T>()

    var value : T? = null

    internal fun post(value: T) {
        this.value = value
        liveData.postValue(Event.Content(value))
    }

    fun observe(owner: LifecycleOwner, observer: Observer<in T>){
        val wrappedObserver = EventObserver(observer)
        liveData.observe(owner, wrappedObserver)
    }

    fun observeForever(observer: Observer<in T>){
        val wrappedObserver = EventObserver(observer)
        liveData.observeForever(wrappedObserver)
    }

    fun removeObserver(observer: Observer<in T>){
        val wrappedObserver = EventObserver(observer)
        liveData.removeObserver(wrappedObserver)
    }

    fun removeObservers(owner: LifecycleOwner){
        liveData.removeObservers(owner)
    }
}