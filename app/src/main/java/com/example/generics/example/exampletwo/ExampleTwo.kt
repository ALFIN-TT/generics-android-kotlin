package com.example.generics.example.exampletwo

import junit.framework.Assert.assertEquals
import org.slf4j.Logger
import org.slf4j.LoggerFactory


fun index() {
    CreateParameterizedClass()
    Covariance()
    Contravariance()
    TypeProjection()
    StarProjections()
    GenericConstraints()
    MultipleUpperBounds()
    TypeErasure()
    TypeErasure()
    ReifiedTypeParameters()
    ReifiedTypeParametersExample()
}

fun CreateParameterizedClass() {

    class CreateParameterizedClass<T>(private val value: T) {
        fun getValue(): T {
            return value
        }
    }
    //We can create an instance of such a class by setting a parameterized type explicitly when using the constructor,
    val parameterizedClass = CreateParameterizedClass<String>("string-value")
    val value = parameterizedClass.getValue()
    assert(value is String)
}


fun Covariance() {
    // We want to create a producer class that will be producing a result of some type T.

    // To achieve that, we need to use the out keyword on the generic type.
    // It means that we can assign this reference to any of its supertypes.
    // The out value can be only be produced by the given class but not consumed:

    class ParameterizedProducer<out T>(private val value: T) {
        fun get(): T {
            return value
        }
    }

    //we can assign an instance of the ParameterizedProducer class to the reference that is a supertype of it,

    val parameterizedProducer = ParameterizedProducer("string")
    val ref: ParameterizedProducer<Any> = parameterizedProducer
    assert(ref is ParameterizedProducer<Any>)

    // subclass <String>
    // baseclass <Any>
    // baseclass <- subclass  | Any <- String
    // assign subclass to baseclass.
}

fun Contravariance() {
    // we want to assign that produced value to a reference that is of a supertype of the type T.
    // we have a reference of type T and we want to be able to assign it to the subtype of T.

    // We can use the in keyword on the generic type if we want to assign it to the reference of its subtype.
    // The in keyword can be used only on the parameter type that is consumed, not produced:

    class ParameterizedConsumer<in T> {
        fun toString(value: T): String {
            return value.toString()
        }
    }

    // toString() method will only be consuming a value of type T

    val parameterizedConsumer = ParameterizedConsumer<Number>()//Baseclass
    val ref: ParameterizedConsumer<Double> = parameterizedConsumer//Subclass
    assert(ref is ParameterizedConsumer<Double>)

    // baseclass <Number>
    // subclass <Double>
    // subclass <- baseclass | Double <- Number
    // assign  baseclass to  subclass.
}

fun TypeProjection() {

    // Let’s say that we have an array of some type, and we want to copy the whole array into the array of Any type. It is a valid operation, but to allow the compiler to compile our code we need to annotate the input parameter with the out keyword.
    // This lets the compiler know that the input argument can be of any type that is a subtype of the Any:

    fun copy(from: Array<out Any>, to: Array<Any?>) {
        assert(from.size == to.size)
        for (i in from.indices)
            to[i] = from[i]
    }

    val ints: Array<Int> = arrayOf(1, 2, 3)
    val any: Array<Any?> = arrayOfNulls(3)

    copy(ints, any)
    // If the from parameter is not of the out Any type, we will not be able to pass an array of an Int type as an argument,
    assertEquals(any[0], 1)
    assertEquals(any[1], 2)
    assertEquals(any[2], 3)

    // we have an array of Any type that is a supertype of Int and we want to add an Int element to this array.
    // We need to use the in keyword as a type of the destination array to let the compiler know that we can copy the Int value to this array:
    fun fill(dest: Array<in Int>, value: Int) {
        dest[0] = value
    }

    // We can copy a value of the Int type to the array of Any.
    val objects: Array<Any?> = arrayOfNulls(1)
    fill(objects, 1)
    assertEquals(objects[0], 1)
}

fun StarProjections() {
    // There are situations when we do not care about the specific type of value.
    // Let’s say that we just want to print all the elements of an array and it does not matter what the type of the elements in this array is.

    fun printArray(array: Array<*>) {
        array.forEach { println(it) }
    }

    val array = arrayOf(1, 2, 3)
    printArray(array)
}

fun GenericConstraints() {

    //we want to sort an array of elements, and each element type should implement a Comparable interface.
    // We can use the generic constraints to specify that requirement,

    fun <T : Comparable<T>> sort(list: List<T>): List<T> {
        return list.sorted()
    }
    // In the given example, we defined that all elements T needed to implement the Comparable interface.
    // Otherwise, if we will try to pass a list of elements that do not implement this interface, it will cause a compiler error.

    // We defined a sort function that takes as an argument a list of elements that implement Comparable, so we can call the sorted() method on it.
    // Let’s look at the test case for that method:

    val listOfInts = listOf(5, 2, 3, 4, 1)
    val sorted = sort(listOfInts)
    assertEquals(sorted, listOf(1, 2, 3, 4, 5))

    fun <T> sort(xs: List<T>) where T : CharSequence, T : Comparable<T> {
        // sort the collection in place
    }
}

fun MultipleUpperBounds() {

    // If a type parameter needs multiple generic upper bounds, then we should use separate where clauses for that particular type parameter.
    fun <T> sort(xs: List<T>) where T : Comparable<T>, T : CharSequence {
        xs.sorted()
    }
    // The parameter T must implement the CharSequence and Comparable interfaces at the same time

    // Declare classes with multiple generic upper bounds,
    class StringCollection<T>(xs: List<T>) where T : Comparable<T>, T : CharSequence {}

    // TODO: Uncomment code
    /* fun <T> Iterable<*>.filterIsInstance() = filter { it is T }*/
}

fun TypeErasure() {
    //Kotlin’s generics are erased at runtime.
    // That is, an instance of a generic class doesn’t preserve its type parameters at runtime.

    val books: Set<String> = setOf("1984", "Brave new world")
    val primes: Set<Int> = setOf(2, 3, 11)

    // At runtime, the type information for Set<String> and Set<Int> will be erased and we see both of them as plain Sets. So, even though it’s perfectly possible to find out at runtime that value is a Set, we can’t tell whether it’s a Set of strings, integers, or something else: that information has been erased.


    // So, how does Kotlin’s compiler prevent us from adding a Non-String into a Set<String>? Or, when we get an element from a Set<String>, how does it know the element is a String?
    // The answer is simple. The compiler is the one responsible for erasing the type information but before that, it actually knows the books variable contains String elements.
    // So, every time we get an element from it, the compiler would cast it to a String or when we’re gonna add an element into it, the compiler would type check the input.

}

inline fun <reified T> Iterable<*>.filterIsInstance() = filter { it is T }

fun ReifiedTypeParameters() {

    //create an extension function to filter Collection elements based on their type
    // TODO: Uncomment code
    /*fun <T> Iterable<*>.filterIsInstance() = filter { it is T }*/
    //Error: Cannot check for instance of erased type: T

    //The “it is T” part, for each collection element, checks if the element is an instance of type T, but since the type information has been erased at runtime, we can’t reflect on type parameters this way.


    //Type parameters of inline functions can be reified, so we can refer to those type parameters at runtime.

    // The body of inline functions is inlined. That is, the compiler substitutes the body directly into places where the function is called instead of the normal function invocation.

    //If we declare the previous function as inline and mark the type parameter as reified, then we can access generic type information at runtime

    //uncomment code
    /*inline fun <reified T> Iterable<*>.filterIsInstance() = filter { it is T }*/

    val set = setOf("1984", 2, 3, "Brave new world", 11)
    println(set.filterIsInstance<Int>())
    //>> [2, 3, 11]
}

inline fun <reified T> logger(): Logger = LoggerFactory.getLogger(T::class.java)
fun ReifiedTypeParametersExample() {
    //SLF4j Logger definitions

    class User1 {
        private val log = LoggerFactory.getLogger(User1::class.java)
    }

    class User {
        private val log = logger<User>()
    }
    //This gives us a cleaner option to implement logging

    // Kotlin’s compiler copies the bytecode of inline functions into places where the function is called.

    // Since in each call site, the compiler knows the exact parameter type, it can replace the generic type parameter with the actual type references.

    //For example, when we write:

    /*class User {
        private val log = logger<User>()
    }*/

    // When the compiler inlines the logger<User>() function call, it knows the actual generic type parameter – User. So instead of erasing the type information, the compiler seizes the reification opportunity and reifies the actual type parameter.
}