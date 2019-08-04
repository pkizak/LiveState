package com.deventium.livestate

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.deventium.livestate.property.LiveProperty
import com.deventium.livestate.state.IState
import com.google.common.truth.Truth
import org.mockito.Mockito

fun <TState : IState, TOut> LiveProperty<TState, TOut>.assertValueEqualTo(other: TOut) {
    Truth.assertThat(this.value).isEqualTo(other)
}

fun <TState : IState, TOut> LiveProperty<TState, TOut>.observe(
    observerActive: Boolean,
    observer: (TOut) -> Unit
): LifecycleRegistry {
    return LifecycleRegistry(mock()).apply {
        markState(if (observerActive) Lifecycle.State.STARTED else Lifecycle.State.INITIALIZED)
    }.also {
        this.observe({ it }) { observer(it) }
    }
}

inline fun <reified TMocked> mock() : TMocked {
    return Mockito.mock(TMocked::class.java)
}