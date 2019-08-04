package com.deventium.livestate.mocks

import androidx.lifecycle.Observer

class MockLiveDataObserver<T> : Observer<T> {
    private var lastValue : T? = null
    val value get() = lastValue
    override fun onChanged(t: T) {
        lastValue = t
    }

}