package com.deventium.livestate.property

import androidx.lifecycle.LiveData
import com.deventium.livestate.state.IState
import com.deventium.livestate.state.IStateStore

abstract class LiveProperty<TState : IState, TOut>(
    protected val stateStore: IStateStore<TState>
) : LiveData<TOut>() {
    private val stateChangedListener by lazy { return@lazy this::onStateChanged }

    override fun onActive() {
        stateStore.registerStateChangeListener(stateChangedListener)
        super.onActive()
    }

    override fun onInactive() {
        super.onInactive()
        stateStore.unregisterStateChangeListener(stateChangedListener)
    }

    protected abstract fun onStateChanged(newState: TState)
}