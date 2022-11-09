package com.example.generics.example.examplefive

import com.example.generics.example.examplefive.ExampleFiveJava.covariantExampleWithRuntimeCrash


fun index() {
    Intro()
    ReviewLimitations()
    PositionsAndTyping()
}

fun Intro() {

    // When a generic type is invariant, like class Box<T>, there is no relation between any Box<SomeType> and Box<AnotherType>. So there is no relation between Box<Number> and Box<Int>.

    //When a generic type is covariant, like class Box<out T>, when A is a subtype of B then Box<A> is a subtype of Box<B>. So Box<Int> is a subtype of Box<Number>.

    // When a generic type is contravariant, like class Box<in T>, when A is a subtype of B then Box<B> is a subtype of Box<A>. So Box<Number> is a subtype of Box<Int>.
}


fun ReviewLimitations() {

    // Kotlin introduced some limitations on type parameters with variance modifiers usage.
    ReviewLimitationsInvariant()
    ReviewLimitationsCovariant()
    ReviewLimitationsContravariant()
}

fun ReviewLimitationsInvariant() {

    //Following class is fully correct:
    class SomeClass<T> {
        var value: T? = null

        fun functionReturningT(): T? = value

        fun functionAcceptingT(value: T) {
            this.value = value
        }

        private fun privateFunctionReturningT(): T? = value

        private fun privateFunctionAcceptingT(value: T) {
            this.value = value
        }
    }
}

fun ReviewLimitationsCovariant() {

    //TODO Uncomment code
    /*class SomeClass<out T> {
        var value: T? = null // Error

        fun functionReturningT(): T? = value

        fun functionAcceptingT(value: T) { // Error
            this.value = value
        }

        private fun privateFunctionReturningT(): T? = value

        private fun privateFunctionAcceptingT(value: T) {
            this.value = value
        }
    }*/

    // As you can see, covariant type can’t be used on public methods as a parameter type and it cannot be used for public read-write properties. Read only are fine because they expose only out position:

    // define some dummy scope to show corrected class (to avoid name conflict)
    run {
        // corrected class.
        class SomeClass<out T> {
            val value: T? = null

            private var privateValue: T? = null

            fun functionReturningT(): T? = value

            private fun privateFunctionReturningT(): T? = value

            private fun privateFunctionAcceptingT(t: T) {}
        }
    }

    // To understand the problem behind this limitations, think of Java arrays. They were covariant and in the same time, they allow setting value (in position). As a result, you can invoke following code which is fully correct from the compilation point of view, but will always result in runtime error:
    covariantExampleWithRuntimeCrash()//
}

fun ReviewLimitationsContravariant() {

    //TODO Uncomment code
    /* class SomeClass<in T> {
         var value: T? = null

         fun functionReturningT(): T? = value

         fun functionAcceptingT(value: T) {
             this.value = value
         }

         private fun privateFunctionReturningT(): T? = value

         private fun privateFunctionAcceptingT(value: T) {
             this.value = value
         }
     }*/

    // Contravariance cannot be used on as a return type from methods and on all methods (getter visibility must be the same as property visibility).
}


fun PositionsAndTyping() {

    //In-positions and out-positions have some default casting contract

    //Class Hierarchy
    //__Any
    //   |______________Animal
    //   |               |
    //   |               |___Human
    //   |_____Wild      |
    //          |        |
    //          |        |
    //          |___Dog__|
    //               |
    //               |__Puppy
    //               |__Hund


    //When we need to pass Dog to in-position, every subtype is accepted as well:
    //refer real image from this path 'assets/positions_and_typing_dog_in_position.png'

    fun takeDog(dog: Dog) {}
    takeDog(Dog())
    takeDog(Puppy())
    takeDog(Hund())


    // When we take Dog from out position, accepted values are Dog and all supertypes:
    //    //refer real image from this path 'assets/positions_and_typing_dog_out_position.png'
    fun makeDog(): Dog = Dog()
    val any: Any = makeDog()
    val animal: Animal = makeDog()
    val wild: Wild = makeDog()

    //Notice that once element is on in or out position, different type of casting is default and it cannot be stopped

    // This is what happened in our array example. Covariance allowed up-casting, and in position allowed down-casting. Using this two mechanism together we can cast to everything. Similarly with contravariance and out position. Together can help developer cast any type to any other. The only problem is that if an actual type cannot be casted this way then we have a runtime error.

    //The only way to prevent this is to prohibit connection of public in-positions and contravariance, and public our-positions and variance. This is why Kotlin has this limitation. Kotlin also solved array problem by making all arrays invariant.
}


