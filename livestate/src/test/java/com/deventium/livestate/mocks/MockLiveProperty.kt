package com.deventium.livestate.mocks

import com.deventium.livestate.property.LiveProperty
import com.deventium.livestate.state.IState
import com.deventium.livestate.state.IStateStore

class MockLiveProperty<TState : IState, TOut>(stateStore: IStateStore<TState>) : LiveProperty<TState, TOut>(stateStore) {
    var stateChangedInvokeCount = 0
    @Suppress("MemberVisibilityCanBePrivate")
    var lastState: TState? = null

    override fun onStateChanged(newState: TState) {
        lastState = newState
        stateChangedInvokeCount++
    }

}