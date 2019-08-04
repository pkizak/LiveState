package com.deventium.livestate.state

import com.deventium.livestate.mocks.MockState
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Test
import java.util.concurrent.CountDownLatch

class StateStoreTests {
    private lateinit var stateStore: StateStore<MockState>
    private val initialState = MockState()
    private lateinit var listener1: MockListener
    private lateinit var listener2: MockListener

    @Before
    fun setup() {
        stateStore = StateStore(initialState)
        listener1 = MockListener()
        listener2 = MockListener()
    }

    @Test
    fun `state property initial value is same as constructor parameter`() {
        Truth.assertThat(stateStore.state).isSameInstanceAs(initialState)
    }

    @Test
    fun `changeState called with lambda returning modified state changes state`() {
        val newState = initialState.copy(intValue = 1)

        stateStore.changeState { newState }

        Truth.assertThat(stateStore.state).isEqualTo(newState)
    }

    @Test
    fun `changeState called with lambda returning modified state notifies listeners with new state`() {
        val newState = initialState.copy(intValue = 1)
        stateStore.registerListeners(listener1, listener2)

        stateStore.changeState { newState }

        Truth.assertThat(listener1.lastState).isEqualTo(newState)
        Truth.assertThat(listener2.lastState).isEqualTo(newState)
    }

    @Test
    fun `changeState called with lambda returning equal state does not notify listeners`() {
        stateStore.registerListeners(listener1, listener2)

        stateStore.changeState { MockState() }

        Truth.assertThat(listener1.invokeCount).isAtMost(1) // 1 because listeners are first notified at registration
        Truth.assertThat(listener2.invokeCount).isAtMost(1)
    }

    @Test
    fun `changeState notifies listeners added multiple times only once with new state on every change`() {
        val newState = initialState.copy(intValue = 1)
        stateStore.registerListeners(listener1, listener1)

        stateStore.changeState { newState }

        Truth.assertThat(listener1.invokeCount).isAtMost(2) // 1. when registered, 2. when changed
        Truth.assertThat(listener1.lastState).isEqualTo(newState)
    }

    @Test
    fun `changeState applies change on top new state when during lambda invocation state is changed by other thread`() {
        val threadAdvanceSignal = CountDownLatch(1)
        val mainAdvanceSignal = CountDownLatch(1)
        stateStore.registerListeners(listener1, listener2)

        Thread {
            threadAdvanceSignal.await() // waiting for main thread to step into changeState lambda
            stateStore.changeState { it.copy(intValue = 1) }
            mainAdvanceSignal.countDown() // allow main thread to change state
        }.start()

        stateStore.changeState {
            threadAdvanceSignal.countDown() // allow other thread to change state
            mainAdvanceSignal.await() // waiting for other thread to end changing state
            it.copy(intValue = it.intValue + 2)
        }

        Truth.assertThat(listener1.lastState!!.intValue).isEqualTo(3)
    }

    @Test
    fun `registerStateChangeListener notifies listeners with current state value`() {
        stateStore.registerListeners(listener1, listener2)

        Truth.assertThat(listener1.lastState).isEqualTo(stateStore.state)
        Truth.assertThat(listener2.lastState).isEqualTo(stateStore.state)
    }

    @Test
    fun `registerStateChangeListener called with same listener multiple times notify listener only once`() {
        stateStore.registerListeners(listener1, listener1)

        Truth.assertThat(listener1.invokeCount).isAtMost(1)
    }

    @Test
    fun `registerStateChangeListener does not throw when called during new state notification on other thread`() {
        val threadAdvanceSignal = CountDownLatch(1)
        val mainAdvanceSignal = CountDownLatch(1)
        val listener = SkipFirstInvokeListener {
            mainAdvanceSignal.countDown()
            threadAdvanceSignal.await()
        }
        stateStore.registerStateChangeListener(listener)
        Thread {
            stateStore.changeState { it.copy(intValue = 1) }
        }.start()
        mainAdvanceSignal.await()
        stateStore.registerStateChangeListener(listener1)
        threadAdvanceSignal.countDown()
    }

    @Test
    fun `unregisterStateChangeListener does not throw when called during new state notification on other thread`() {
        val threadAdvanceSignal = CountDownLatch(1)
        val mainAdvanceSignal = CountDownLatch(1)
        val listener = SkipFirstInvokeListener {
            mainAdvanceSignal.countDown()
            threadAdvanceSignal.await()
        }
        stateStore.registerStateChangeListener(listener)
        stateStore.registerStateChangeListener(listener1)
        Thread {
            stateStore.changeState { it.copy(intValue = 1) }
        }.start()
        mainAdvanceSignal.await()
        stateStore.unregisterStateChangeListener(listener1)
        threadAdvanceSignal.countDown()
    }

    @Test
    fun `unregisterChangeStateListener stops notifications on removed listeners`() {
        val newState = initialState.copy(intValue = 1)
        stateStore.registerListeners(listener1, listener2)

        stateStore.unregisterStateChangeListener(listener1)
        stateStore.changeState { newState }

        Truth.assertThat(listener1.lastState).isEqualTo(initialState)
        Truth.assertThat(listener2.lastState).isEqualTo(newState)
    }

    private fun StateStore<MockState>.registerListeners(vararg listeners: MockListener) {
        for (listener in listeners) {
            this.registerStateChangeListener(listener)
        }
    }

    private class MockListener : (MockState) -> Unit {
        var lastState: MockState? = null
        var invokeCount: Int = 0
        override fun invoke(p1: MockState) {
            lastState = p1
            invokeCount++
        }
    }

    private class SkipFirstInvokeListener(private val func: (MockState) -> Unit) : (MockState) -> Unit {
        @Volatile
        private var consecutiveInvocation = false

        override fun invoke(p1: MockState) {
            if (consecutiveInvocation) {
                func(p1)
            } else {
                consecutiveInvocation = true
            }

        }
    }
}