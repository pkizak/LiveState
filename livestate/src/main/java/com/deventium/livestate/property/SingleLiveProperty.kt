package com.deventium.livestate.property

import com.deventium.livestate.state.IState
import com.deventium.livestate.state.IStateStore
import kotlin.reflect.KProperty1

internal class SingleLiveProperty<TState : IState, TStateProperty>(
    stateStore: IStateStore<TState>,
    private val stateProperty: KProperty1<in TState, TStateProperty>
) : LiveProperty<TState, TStateProperty>(stateStore) {

    init {
        setValue(stateProperty.get(stateStore.state))
    }

    override fun onStateChanged(newState: TState) {
        val statePropertyValue = stateProperty.get(newState)
        super.postValue(statePropertyValue)
    }

    // LiveData's setValue notifies observers even if current value is the same as new one.
    // We want to override this behavior so that observers are notified only if new value is different (by means of equals()) than currently set.
    override fun setValue(value: TStateProperty) {
        if (value == this.value) return
        super.setValue(value)
    }

}