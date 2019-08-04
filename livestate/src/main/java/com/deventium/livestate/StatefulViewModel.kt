package com.deventium.livestate

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.deventium.livestate.event.LiveEvent
import com.deventium.livestate.property.DefaultLivePropertyFactory
import com.deventium.livestate.property.LivePropertyFactory
import com.deventium.livestate.property.ValuesHolder
import com.deventium.livestate.state.IState
import com.deventium.livestate.state.IStateStore
import com.deventium.livestate.state.StateStore
import kotlin.reflect.KProperty1


@Suppress("UNCHECKED_CAST", "unused")
abstract class StatefulViewModel<TState : IState>(
    initialState: TState,
    private val stateStore: IStateStore<TState> = StateStore(initialState),
    private val livePropertyFactory: LivePropertyFactory<TState> = DefaultLivePropertyFactory(stateStore)
) : ViewModel() {

    val state: TState get() = stateStore.state

    /**
     * This method allows to change ViewModel's state in thread-safe way.
     *
     * @param func does actual state change. Passed function should be as simple as possible and idempotent, because it could be invoked multiple times.
     */
    protected fun changeState(func: (TState) -> TState) {
        stateStore.changeState(func)
    }

    protected fun <TStateProperty> liveProperty(prop: KProperty1<in TState, TStateProperty>): LiveData<TStateProperty> {
        return livePropertyFactory.getSingleLiveProperty(prop)
    }

    protected fun <TOut> liveProperty(
        vararg properties: KProperty1<in TState, *>,
        mappingFunc: (ValuesHolder) -> TOut
    ): LiveData<TOut> {
        return livePropertyFactory.getMultiLiveProperty(*properties).map(mappingFunc)
    }

    protected fun <TIn0, TIn1, TOut> liveProperty(
        prop0: KProperty1<in TState, TIn0>,
        prop1: KProperty1<in TState, TIn1>,
        mappingFunc: (TIn0, TIn1) -> TOut
    ): LiveData<TOut> {
        return livePropertyFactory.getMultiLiveProperty(prop0, prop1)
            .map { mappingFunc(it[0], it[1]) }
    }

    protected fun <TIn0, TIn1, TIn2, TOut> liveProperty(
        prop0: KProperty1<in TState, TIn0>,
        prop1: KProperty1<in TState, TIn1>,
        prop2: KProperty1<in TState, TIn2>,
        mappingFunc: (TIn0, TIn1, TIn2) -> TOut
    ): LiveData<TOut> {
        return livePropertyFactory.getMultiLiveProperty(prop0, prop1, prop2)
            .map { mappingFunc(it[0], it[1], it[2]) }
    }

    protected fun <TIn0, TIn1, TIn2, TIn3, TOut> liveProperty(
        prop0: KProperty1<in TState, TIn0>,
        prop1: KProperty1<in TState, TIn1>,
        prop2: KProperty1<in TState, TIn2>,
        prop3: KProperty1<in TState, TIn3>,
        mappingFunc: (TIn0, TIn1, TIn2, TIn3) -> TOut
    ): LiveData<TOut> {
        return livePropertyFactory.getMultiLiveProperty(prop0, prop1, prop2, prop3)
            .map { mappingFunc(it[0], it[1], it[2], it[3]) }
    }

    protected fun <TIn0, TIn1, TIn2, TIn3, TIn4, TOut> liveProperty(
        prop0: KProperty1<in TState, TIn0>,
        prop1: KProperty1<in TState, TIn1>,
        prop2: KProperty1<in TState, TIn2>,
        prop3: KProperty1<in TState, TIn3>,
        prop4: KProperty1<in TState, TIn4>,
        mappingFunc: (TIn0, TIn1, TIn2, TIn3, TIn4) -> TOut
    ): LiveData<TOut> {
        return livePropertyFactory.getMultiLiveProperty(prop0, prop1, prop2, prop3, prop4)
            .map { mappingFunc(it[0], it[1], it[2], it[3], it[4]) }
    }

    protected fun <TIn0, TIn1, TIn2, TIn3, TIn4, TIn5, TOut> liveProperty(
        prop0: KProperty1<in TState, TIn0>,
        prop1: KProperty1<in TState, TIn1>,
        prop2: KProperty1<in TState, TIn2>,
        prop3: KProperty1<in TState, TIn3>,
        prop4: KProperty1<in TState, TIn4>,
        prop5: KProperty1<in TState, TIn5>,
        mappingFunc: (TIn0, TIn1, TIn2, TIn3, TIn4, TIn5) -> TOut
    ): LiveData<TOut> {
        return livePropertyFactory.getMultiLiveProperty(prop0, prop1, prop2, prop3, prop4, prop5)
            .map { mappingFunc(it[0], it[1], it[2], it[3], it[4], it[5]) }
    }

    protected fun <TIn0, TIn1, TIn2, TIn3, TIn4, TIn5, TIn6, TOut> liveProperty(
        prop0: KProperty1<in TState, TIn0>,
        prop1: KProperty1<in TState, TIn1>,
        prop2: KProperty1<in TState, TIn2>,
        prop3: KProperty1<in TState, TIn3>,
        prop4: KProperty1<in TState, TIn4>,
        prop5: KProperty1<in TState, TIn5>,
        prop6: KProperty1<in TState, TIn6>,
        mappingFunc: (TIn0, TIn1, TIn2, TIn3, TIn4, TIn5, TIn6) -> TOut
    ): LiveData<TOut> {
        return livePropertyFactory.getMultiLiveProperty(prop0, prop1, prop2, prop3, prop4, prop5, prop6)
            .map { mappingFunc(it[0], it[1], it[2], it[3], it[4], it[5], it[6]) }
    }

    protected fun <TIn0, TIn1, TIn2, TIn3, TIn4, TIn5, TIn6, TIn7, TOut> liveProperty(
        prop0: KProperty1<in TState, TIn0>,
        prop1: KProperty1<in TState, TIn1>,
        prop2: KProperty1<in TState, TIn2>,
        prop3: KProperty1<in TState, TIn3>,
        prop4: KProperty1<in TState, TIn4>,
        prop5: KProperty1<in TState, TIn5>,
        prop6: KProperty1<in TState, TIn6>,
        prop7: KProperty1<in TState, TIn7>,
        mappingFunc: (TIn0, TIn1, TIn2, TIn3, TIn4, TIn5, TIn6, TIn7) -> TOut
    ): LiveData<TOut> {
        return livePropertyFactory.getMultiLiveProperty(
            prop0,
            prop1,
            prop2,
            prop3,
            prop4,
            prop5,
            prop6,
            prop7
        )
            .map { mappingFunc(it[0], it[1], it[2], it[3], it[4], it[5], it[6], it[7]) }
    }

    protected fun <TIn0, TIn1, TIn2, TIn3, TIn4, TIn5, TIn6, TIn7, TIn8, TOut> liveProperty(
        prop0: KProperty1<in TState, TIn0>,
        prop1: KProperty1<in TState, TIn1>,
        prop2: KProperty1<in TState, TIn2>,
        prop3: KProperty1<in TState, TIn3>,
        prop4: KProperty1<in TState, TIn4>,
        prop5: KProperty1<in TState, TIn5>,
        prop6: KProperty1<in TState, TIn6>,
        prop7: KProperty1<in TState, TIn7>,
        prop8: KProperty1<in TState, TIn8>,
        mappingFunc: (TIn0, TIn1, TIn2, TIn3, TIn4, TIn5, TIn6, TIn7, TIn8) -> TOut
    ): LiveData<TOut> {
        return livePropertyFactory.getMultiLiveProperty(
            prop0,
            prop1,
            prop2,
            prop3,
            prop4,
            prop5,
            prop6,
            prop7,
            prop8
        )
            .map { mappingFunc(it[0], it[1], it[2], it[3], it[4], it[5], it[6], it[7], it[8]) }
    }

    private inline fun <TIn, TOut> LiveData<TIn>.map(crossinline mapFunc: (TIn) -> TOut): LiveData<TOut> {
        val mapper = MediatorLiveData<TOut>()
        mapper.value = this.value?.let(mapFunc)
        mapper.addSource(this) { x -> mapper.setValue(mapFunc(x)) }
        return mapper
    }

    @Suppress("MemberVisibilityCanBePrivate")
    protected fun <T> liveEvent(): LiveEvent<T> {
        return LiveEvent()
    }

    @JvmName("simpleLiveEvent")
    protected fun liveEvent(): LiveEvent<Unit> {
        return liveEvent<Unit>()
    }

    protected fun LiveEvent<Unit>.post() {
        this.post(Unit)
    }

    protected fun <T> LiveEvent<T>.post(content: T) {
        this.post(content)
    }

}

