package com.deventium.livestate.mocks

import com.deventium.livestate.state.IStateStore
import com.deventium.livestate.state.StateChangeListener

class MockStateStore(initialState: MockState) : IStateStore<MockState> {

    override var state: MockState = initialState

    private val listeners = mutableSetOf<StateChangeListener<in MockState>>()

    override fun changeState(func: (currentState: MockState) -> MockState) {
        state = func(state)
        listeners.forEach { it(state) }
    }

    override fun registerStateChangeListener(listener: StateChangeListener<in MockState>) {
        listeners.add(listener)
    }

    override fun unregisterStateChangeListener(listener: StateChangeListener<in MockState>) {
        listeners.remove(listener)
    }
}