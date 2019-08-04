package com.deventium.livestate.property

import com.deventium.livestate.state.IState
import com.deventium.livestate.state.IStateStore
import kotlin.reflect.KProperty1

internal class MultiLiveProperty<TState : IState>(
    stateStore: IStateStore<TState>,
    private val stateProperties: Array<out KProperty1<in TState, *>>
) : LiveProperty<TState, ValuesHolder>(stateStore) {

    init {
        setInitialValue()
    }

    private fun setInitialValue() {
        val propertiesInitialValues = stateProperties.map { it.get(stateStore.state) }
        setValue(ValuesHolder(propertiesInitialValues))
    }

    override fun onStateChanged(newState: TState) {
        val statePropertiesValues = stateProperties.map { it.get(newState) }
        super.postValue(ValuesHolder(statePropertiesValues))
    }

    // LiveData's setValue notifies observers even if current value is the same as new one.
    // We want to override this behavior so that observers are notified only if new value is different (by means of equals()) than currently set.
    override fun setValue(value: ValuesHolder) {
        if (value == this.value) return
        super.setValue(value)
    }
}