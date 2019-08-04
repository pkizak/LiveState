package com.deventium.livestate.property

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.deventium.livestate.mocks.MockState
import com.deventium.livestate.mocks.MockStateStore
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DefaultLivePropertyFactoryTests {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val stateStore: MockStateStore = MockStateStore(MockState())
    private lateinit var factory: DefaultLivePropertyFactory<MockState>

    @Before
    fun setup(){
        factory = DefaultLivePropertyFactory(stateStore)
    }

    @Test
    fun `getMultiLiveProperty returns MultiLiveProperty class`(){

        val result = factory.getMultiLiveProperty()

        Truth.assertThat(result).isInstanceOf(MultiLiveProperty::class.java)
    }

    @Test
    fun `getSingleLiveProperty returns SingleLiveProperty class`(){

        val result = factory.getSingleLiveProperty(MockState::intValue)

        Truth.assertThat(result).isInstanceOf(SingleLiveProperty::class.java)
    }

    @Test
    fun `getSingleLiveProperty returns same instance of LiveProperty for same state property`(){

        val result = factory.getSingleLiveProperty(MockState::intValue)
        val result2 = factory.getSingleLiveProperty(MockState::intValue)

        Truth.assertThat(result).isSameInstanceAs(result2)
    }
}