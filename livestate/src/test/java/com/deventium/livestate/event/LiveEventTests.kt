package com.deventium.livestate.event

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import com.deventium.livestate.mocks.MockLiveDataObserver
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LiveEventTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private lateinit var event : LiveEvent<Int>

    @Before
    fun setup(){
        event = LiveEvent()
    }

    @Test
    fun `value is initially null`(){
        Truth.assertThat(event.value).isNull()
    }

    @Test
    fun `post sets value property`(){
        val value = 1

        event.post(1)

        Truth.assertThat(event.value).isEqualTo(value)
    }

    @Test
    fun `post sends value to active observer added with owner`(){
        val value = 1
        val owner = MockLifecycleOwner()
        val observer = MockLiveDataObserver<Int>()
        event.observe(owner, observer)
        owner.registry.markState(Lifecycle.State.STARTED)

        event.post(value)

        Truth.assertThat(observer.value).isEqualTo(value)
    }

    @Test
    fun `post sends value to observer added without owner`(){
        val value = 1
        val observer = MockLiveDataObserver<Int>()
        event.observeForever(observer)

        event.post(value)

        Truth.assertThat(observer.value).isEqualTo(value)
    }

    @Test
    fun `post sends value to multiple active observers`(){
        val value = 1
        val owner = MockLifecycleOwner()
        val observer = MockLiveDataObserver<Int>()
        val observer2 = MockLiveDataObserver<Int>()
        event.observe(owner, observer)
        event.observeForever(observer2)
        owner.registry.markState(Lifecycle.State.STARTED)

        event.post(value)

        Truth.assertThat(observer.value).isEqualTo(value)
        Truth.assertThat(observer2.value).isEqualTo(value)
    }

    @Test
    fun `post does not sends value to inactive observer`(){
        val value = 1
        val owner = MockLifecycleOwner()
        val observer = MockLiveDataObserver<Int>()
        event.observe(owner, observer)

        event.post(value)

        Truth.assertThat(observer.value).isNull()
    }

    @Test
    fun `post does not sends value to inactive observer, that became active after post`(){
        val value = 1
        val owner = MockLifecycleOwner()
        val observer = MockLiveDataObserver<Int>()
        event.observe(owner, observer)

        event.post(value)
        owner.registry.markState(Lifecycle.State.STARTED)

        Truth.assertThat(observer.value).isNull()
    }


    @Test
    fun `observe does not dispatch current value when active observer is added`(){
        val value = 1
        val owner = MockLifecycleOwner()
        val observer = MockLiveDataObserver<Int>()
        owner.registry.markState(Lifecycle.State.STARTED)
        event.post(value)

        event.observe(owner, observer)

        Truth.assertThat(observer.value).isNull()
    }

    @Test
    fun `removeObserver stops observing LiveEvent`(){
        val value = 1
        val secondValue = 2
        val observer = MockLiveDataObserver<Int>()
        event.observeForever(observer)
        event.post(value)

        event.removeObserver(observer)
        event.post(secondValue)

        Truth.assertThat(observer.value).isNotEqualTo(secondValue)
    }

    @Test
    fun `removeObservers stops observing LiveEvent for all observers added with this owner`(){
        val value = 1
        val secondValue = 2
        val owner = MockLifecycleOwner()
        val observer = MockLiveDataObserver<Int>()
        val observer2 = MockLiveDataObserver<Int>()

        event.observe(owner, observer)
        event.observe(owner, observer2)
        owner.registry.markState(Lifecycle.State.STARTED)
        event.post(value)

        event.removeObservers(owner)
        event.post(secondValue)

        Truth.assertThat(observer.value).isNotEqualTo(secondValue)
        Truth.assertThat(observer2.value).isNotEqualTo(secondValue)
    }

    class MockLifecycleOwner : LifecycleOwner {
        val registry = LifecycleRegistry(this).apply { markState(Lifecycle.State.INITIALIZED) }
        override fun getLifecycle(): Lifecycle {
            return registry
        }

    }
}