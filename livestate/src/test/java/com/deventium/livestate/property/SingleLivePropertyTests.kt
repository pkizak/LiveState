package com.deventium.livestate.property

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import com.deventium.livestate.assertValueEqualTo
import com.deventium.livestate.mocks.MockState
import com.deventium.livestate.mocks.MockStateStore
import com.deventium.livestate.observe
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SingleLivePropertyTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val initialState = MockState()
    private lateinit var stateStore: MockStateStore
    private lateinit var liveProperty: SingleLiveProperty<MockState, Int>

    @Before
    fun setup() {
        stateStore = MockStateStore(initialState)
        liveProperty = SingleLiveProperty(stateStore, MockState::intValue)
    }

    @Test
    fun `initial value is equal to observed current state's value`() {
        liveProperty.assertValueEqualTo(initialState.intValue)
    }

    @Test
    fun `observe does not notify observers if state changes but observed value does not`() {
        var observerInvokeCount = 0
        liveProperty.observe(true) { observerInvokeCount++ }

        stateStore.changeState { it.copy(booleanValue = true) }

        Truth.assertThat(observerInvokeCount).isAtMost(1)
    }

    @Test
    fun `value is not changed when state's value is changed but observers are not active`() {
        val lifecycle = liveProperty.observe(false) { }

        stateStore.changeState { it.copy(intValue = it.intValue + 1) }
        lifecycle.markState(Lifecycle.State.STARTED)

        liveProperty.assertValueEqualTo(initialState.intValue)
    }

    @Test
    fun `value is changed when state's value is changed and observers are active`() {
        liveProperty.observe(true) { }

        stateStore.changeState { it.copy(intValue = it.intValue + 1) }

        liveProperty.assertValueEqualTo(initialState.intValue + 1)
    }

}