package com.deventium.livestate.state

import com.deventium.livestate.extensions.supportDistinctUpdateAndGet
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference

internal class StateStore<TState: IState>(initialState: TState) : IStateStore<TState>{

    private val _state = AtomicReference(initialState)

    private val _stateChangeListeners = Collections.newSetFromMap(ConcurrentHashMap<StateChangeListener<in TState>, Boolean>())

    override val state : TState get() = _state.get()

    override fun changeState(func: (currentState: TState) -> TState){
        val newState = _state.supportDistinctUpdateAndGet(func) ?: return
        _stateChangeListeners.forEach { it.invoke(newState) }
    }

    override fun registerStateChangeListener(listener: StateChangeListener<in TState>) {
        if (_stateChangeListeners.add(listener)){
            listener.invoke(state)
        }
    }

    override fun unregisterStateChangeListener(listener: StateChangeListener<in TState>) {
        _stateChangeListeners.remove(listener)
    }
}