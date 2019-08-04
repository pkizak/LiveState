package com.deventium.livestate.mocks

import android.os.Parcel
import com.deventium.livestate.state.IState

data class MockState(val intValue: Int = 0, val stringValue: String = "", val booleanValue: Boolean = false) :
    IState {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        // not relevant for testing
    }

    override fun describeContents(): Int {
        return 0
        // not relevant for testing
    }
}