package com.example.generics.example.exampleseven


fun index() {
    BasicGenerics()
    Covariance()
    Contravariance()
    StarProjections()
}

fun BasicGenerics() {
    //The generic class hold any type
    Company<String>("holds value of string")
    Company<Int>(0)
    Company<Double>(1.0)
    Company<Float>(1F)

    Company("holds value of string")
    Company(123)
    Company(1F)
}


fun Covariance() {
    //Kotlin program of copying elements of one array into another
    fun copy(from: Array<out Any>, to: Array<Any>) {
        assert(from.size == to.size)
        // copying (from) array to (to) array
        for (i in from.indices) to[i] = from[i]
        // printing elements of array in which copied
        for (i in to.indices) println(to[i])
    }

    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any1: Array<Any> = Array<Any>(3) { "" }
    copy(ints, any1)

    val strings: Array<String> = arrayOf("a", "b", "c")
    val any2: Array<Any> = Array<Any>(3) { 1 }
    copy(strings, any2)

}

fun Contravariance() {
    val car: ServiceCenter<Creta> = ServiceCenter<Hyundai>()
    //TODO uncomment code
    /*val hyundaiCar: ServiceCenter<Hyundai> = ServiceCenter<Creta>()*/
}

fun StarProjections() {

    //When we do not know about the specific type of the value and we just want to print all the elements of an array then we use star(*) projection.
    val names = arrayOf("Android", "Kotlin", "Java", "Flutter", "Dart")
    printArray(names)
}


