package com.example.generics.example.exampleseven


class Company<T>(private val param: T) {
    var value = param

    init {
        println(value)
    }
}


open class Hyundai
class Venue : Hyundai()
class Creta : Hyundai()

class ServiceCenter<in T>

fun printArray(array: Array<*>) {
    array.forEach {
        println(it)
    }
}
