package com.example.generics.example.exampletwo


class ParameterizedClass<A>(private val value: A) {

    fun getValue(): A {
        return value
    }
}

class ParameterizedProducer<out T>(private val value: T) {
    fun get(): T {
        return value
    }
}

class ParameterizedConsumer<in T> {
    fun toString(value: T): String {
        return value.toString()
    }
}

fun copy(from: Array<out Any>, to: Array<Any?>) {
    assert(from.size == to.size)
    for (i in from.indices)
        to[i] = from[i]
}

fun fill(dest: Array<in Int>, value: Int) {
    dest[0] = value
}

fun printArray(array: Array<*>) {
    array.forEach { println(it) }
}

fun <T : Comparable<T>> sort(list: List<T>): List<T> {
    return list.sorted()
}