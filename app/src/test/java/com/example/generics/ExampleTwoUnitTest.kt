package com.example.generics

import com.example.generics.example.exampletwo.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleTwoUnitTest {


    @Test
    fun givenParametrizeClass_whenInitializeItWithSpecificType_thenShouldBeParameterized() {
        val parameterizedClass = ParameterizedClass<String>("string-value")
        val res = parameterizedClass.getValue()
        assertTrue(res is String)
    }

    @Test
    fun givenParametrizeClass_whenInitializeIt_thenShouldBeParameterizedByInferredType() {

        val parameterizedClass = ParameterizedClass("string-value")
        val res = parameterizedClass.getValue()
        assertTrue(res is String)
    }

    @Test
    fun givenParameterizedProducerByOutKeyword_whenGetValue_thenCanAssignItToSuperType() {

        val parameterizedProducer = ParameterizedProducer("string")
        val ref: ParameterizedProducer<Any> = parameterizedProducer
        assertTrue(ref is ParameterizedProducer<Any>)
    }

    @Test
    fun givenParameterizedConsumerByInKeyword_whenGetValue_thenCanAssignItToSubType() {

        val parameterizedConsumer = ParameterizedConsumer<Number>()
        val ref: ParameterizedConsumer<Double> = parameterizedConsumer
        assertTrue(ref is ParameterizedConsumer<Double>)
    }

    @Test
    fun givenTypeProjections_whenOperateOnTwoList_thenCanAcceptListOfSubtypes() {

        val ints: Array<Int> = arrayOf(1, 2, 3)
        val any: Array<Any?> = arrayOfNulls(3)

        copy(ints, any)
        assertEquals(any[0], 1)
        assertEquals(any[1], 2)
        assertEquals(any[2], 3)
    }

    @Test
    fun givenTypeProjection_whenHaveArrayOfIn_thenShouldAddElementsOfSubtypesToIt() {
        val objects: Array<Any?> = arrayOfNulls(1)
        fill(objects, 1)
        assertEquals(objects[0], 1)
    }

    @Test
    fun givenStartProjection_whenPassAnyType_thenCompile() {
        val array = arrayOf(1, 2, 3)
        printArray(array)
    }

    @Test
    fun givenFunctionWithDefinedGenericConstraints_whenCallWithProperType_thenCompile() {
        val listOfInts = listOf(5, 2, 3, 4, 1)
        val sorted = sort(listOfInts)
        assertEquals(sorted, listOf(1, 2, 3, 4, 5))
    }
}