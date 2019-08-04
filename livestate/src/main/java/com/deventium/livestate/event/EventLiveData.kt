package com.deventium.livestate.event

import androidx.lifecycle.LiveData

internal class EventLiveData<T> : LiveData<Event<T>>(){
    init {
        super.setValue(Event.Empty)
    }
    public override fun postValue(value: Event<T>?) {
        super.postValue(value)
    }
    override fun setValue(value: Event<T>) {
        super.setValue(value)
        super.setValue(Event.Empty)
    }
}