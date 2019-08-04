package com.deventium.livestate.property

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleRegistry
import com.deventium.livestate.mock
import com.deventium.livestate.mocks.MockLiveProperty
import com.deventium.livestate.mocks.MockState
import com.deventium.livestate.mocks.MockStateStore
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LivePropertyTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var mockStateStore: MockStateStore
    private lateinit var liveProperty: MockLiveProperty<MockState, Any>

    @Before
    fun setup() {
        mockStateStore = MockStateStore(MockState())
        liveProperty = MockLiveProperty(mockStateStore)
    }

    @Test
    fun `onChangeState is not invoked when liveProperty is not observed`() {
        mockStateStore.changeState { it.copy(intValue = 1) }

        Truth.assertThat(liveProperty.stateChangedInvokeCount).isEqualTo(0)
    }

    @Test
    fun `onChangeState is invoked when liveProperty has active observer`() {
        liveProperty.observeForever { }
        mockStateStore.changeState { it.copy(intValue = 1) }

        Truth.assertThat(liveProperty.stateChangedInvokeCount).isAtLeast(1)
    }

    @Test
    fun `onChangeState is not invoked when liveProperty has inactive observer`() {
        val lifecycle = LifecycleRegistry(mock())
        liveProperty.observe({ lifecycle }) {}
        lifecycle.markState(Lifecycle.State.INITIALIZED)

        mockStateStore.changeState { it.copy(intValue = 1) }

        Truth.assertThat(liveProperty.stateChangedInvokeCount).isEqualTo(0)
    }


}