package com.deventium.livestate.property

import com.deventium.livestate.state.IState
import kotlin.reflect.KProperty1

abstract class LivePropertyFactory<TState: IState>{
    abstract fun <TStateProperty> getSingleLiveProperty(stateProperty: KProperty1<in TState, TStateProperty>) : LiveProperty<TState, TStateProperty>
    abstract fun getMultiLiveProperty(vararg stateProperties: KProperty1<in TState, *>): LiveProperty<TState, ValuesHolder>
}