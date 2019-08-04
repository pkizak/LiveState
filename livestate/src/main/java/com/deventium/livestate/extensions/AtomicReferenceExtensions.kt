package com.deventium.livestate.extensions

import java.util.concurrent.atomic.AtomicReference

inline fun <T> AtomicReference<T>.supportDistinctUpdateAndGet(func: (T) -> T) : T?{
    var prev: T
    var next: T

    do {
        prev = get()
        next = func.invoke(prev)
    } while (!compareAndSet(prev, next))
    if (prev == next) return null
    return next
}