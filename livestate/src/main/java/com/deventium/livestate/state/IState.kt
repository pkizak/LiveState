package com.deventium.livestate.state

import android.os.Parcelable

interface IState : Parcelable {
    @Suppress("unused")
    fun getSaveable() : IState = this
}