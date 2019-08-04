package com.deventium.livestate.event

import androidx.lifecycle.Observer

internal inline class EventObserver<T>(private val observer: Observer<T>) : Observer<Event<T>> {
    override fun onChanged(t: Event<T>?) {
        if (t is Event.Content) {
            observer.onChanged(t.content)
        }
    }
}