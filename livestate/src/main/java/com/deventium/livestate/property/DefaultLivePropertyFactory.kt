package com.deventium.livestate.property

import com.deventium.livestate.state.IState
import com.deventium.livestate.state.IStateStore
import kotlin.reflect.KProperty1

internal class DefaultLivePropertyFactory<TState : IState>(private val stateStore: IStateStore<TState>) : LivePropertyFactory<TState>(){

    private val livePropertyHashMap = hashMapOf<KProperty1<in TState, *>, LiveProperty<TState, *>>()

    override fun <TStateProperty> getSingleLiveProperty(stateProperty: KProperty1<in TState, TStateProperty>): LiveProperty<TState, TStateProperty> {
        //HashMap is private and this is the only place it's modified. Cast is safe here.
        @Suppress("UNCHECKED_CAST")
        return livePropertyHashMap.getOrPut(stateProperty) {
            SingleLiveProperty(stateStore, stateProperty)
        } as LiveProperty<TState, TStateProperty>
    }

    override fun getMultiLiveProperty(vararg stateProperties: KProperty1<in TState, *>): LiveProperty<TState, ValuesHolder> {
        return MultiLiveProperty(stateStore, stateProperties)
    }

}