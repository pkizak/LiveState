package com.deventium.livestate.state

interface IStateStore<TState: IState> {
    val state : TState

    fun changeState(func: (currentState: TState) -> TState)

    fun registerStateChangeListener(listener: StateChangeListener<in TState>)

    fun unregisterStateChangeListener(listener: StateChangeListener<in TState>)
}