package com.deventium.livestate.property

@Suppress("UNCHECKED_CAST")
inline class ValuesHolder(private val values: List<Any?>) {

    private fun <T> elementAt(i: Int): T =
            if (values.size > i) values[i] as T else throw NoSuchElementException("Can't get value #$i from $this")

    operator fun component1() : Any? = elementAt(0)
    operator fun component2() : Any? = elementAt(1)
    operator fun component3() : Any? = elementAt(2)
    operator fun component4() : Any? = elementAt(3)
    operator fun component5() : Any? = elementAt(4)
    operator fun component6() : Any? = elementAt(5)
    operator fun component7() : Any? = elementAt(6)
    operator fun component8() : Any? = elementAt(7)

    /**
     * get element at given index
     * return T
     */
    operator fun <T> get(i: Int) = values[i] as T

}