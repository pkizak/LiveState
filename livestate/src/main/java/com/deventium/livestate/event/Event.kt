package com.deventium.livestate.event

internal sealed class Event<out T>{
    data class Content<T>(val content: T) : Event<T>()
    object Empty : Event<Nothing>()
}