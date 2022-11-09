package com.example.generics.example.examplethree


fun index() {
    CreateParameterizedClass()
    Variance()
    Covariance()
    Contravariance()
    Invariance()
    RestrictinUsage()
    TypeErasure()
}

fun CreateParameterizedClass() {

    // We define a stack that can be used for the sole purpose of handling integers,
    val intStack: IntStack = IntStack()
    intStack.push(0)
    intStack.pop()
    //in this stack only hold integer values

    //We define another class that can be used for any type T
    val stackInt: Stack<Int> = Stack()//for integer
    stackInt.push(0)
    stackInt.pop()

    val stackString: Stack<String> = Stack()//for strings
    stackString.push("0")
    stackString.pop()
}

fun Variance() {
    val animals: MutableList<Mammal> = mutableListOf()
    animals.add(Dog())
    animals.add(Cat())

    //In the code above, variance tells us that a Dog and a Cat will have the same rights in a list that’s defined as List<Mammal>.

    //The code below would work, too:
    val dogs: List<Dog> = listOf(Dog())
    val mammal: Mammal = dogs.first()
}


fun Covariance() {

    //TODO uncomment code below.
    /*val petOwner1: PetOwner<Mammal> = PetOwner<Cat>()*/
    // This won't work: it's a type mismatch

    val petOwner2: PetOwner<out Mammal> = PetOwner<Cat>()
    // This will work: we tell the compiler that petOwner2 accepts lists of its type's subtypes too

    //Covariance is very useful when we want to limit our usage to subtypes only:
    val mammals: List<out Mammal> = listOf(Dog(), Cat())
    mammals.forEach { mammal -> mammal.move() }
    //By instantiating our mammals list with the above syntax, we ensure that only subtypes of the type Mammal can be contained in and retrieved from a list.
}

fun Contravariance() {

    // But what if we wanted to do an operation only on those members that have a certain degree of rights and responsibilities in our scenario?
    // This is where we’d want to set a lower boundary. More specifically, when using the syntax Stack<in T>, we are able to only manipulate objects that are at most of type T.

    val superUsersList: MutableList<in Moderator> = mutableListOf()
    // With the above syntax, we are therefore creating a list that will only accept objects of type Moderator and above (such as User, the supertype of User — if it has one — and so on).


    val userComparator: Comparator<User> = object : Comparator<User> {
        override fun compare(firstUser: User, secondUser: User): Int {
            return firstUser.point - secondUser.point
        }
    }

    val moderatorComparator: Comparator<in Moderator> = userComparator

    // The above syntax is correct. What we’re doing is defining a comparator that can be used for any kind of user. Then we declare a comparator that only applies to moderators and assigns to it the users comparator. This is acceptable since a Moderator is a subtype of User.

    //How is this situation contravariant? The userCompare comparator specializes in a superclass, whereas the moderator comparator is a subclass that can be assigned a value that depends on its superclass.
}

fun Invariance() {

    // every class that you define with a generic type with no in or out keyword will be considered to be invariant.
    // This is because there will be no relationship between the types that you created using generics.
    // All Kotlin generic types are invariant by default

    open class Animal

    class Dog : Animal()

    val animals: MutableList<Animal> = mutableListOf()
    val dogs: MutableList<Dog> = mutableListOf()

    // In the above example, we see that there’s a clear relationship between Dog and Animal: the former is a subtype of the latter. However, we can’t say the same about the types of the two list variables. There is no relationship between those two. Therefore, we can say that List is invariant on its type parameter.
}

fun RestrictinUsage() {

    //In some situations, we must use generics with our method definitions such that the parameters passed to them will respect a set of prerequisites. These prerequisites ensure that our code can actually run.

    fun <T : Comparable<T>> sort(list: List<T>): List<T> {
        return list.sorted()
    }
    //From the above function declaration, we understand that we can strictly use the sort method on lists that contain object instantiations of classes that implement the Comparable interface.


    val students: MutableList<Student> = mutableListOf(Girl(11), Boy(12))
    sort(students)

    //If we were to call the sort method on a subtype of Student, the compiler would throw an error. However, it will work with the Student class since it implements the compareTo method.
}

fun TypeErasure() {
    // The compiler wants to make sure that types are not available to us at runtime. This is the reason why the following code would not compile:

    class SimpleClass {

        //TODO uncomment code.
        /* fun doSomething(list: List<String>): Int {
             return 0
         }*/

        //TODO uncomment code.
        /* fun doSomething(list: List<Int>): Int {
              return 0
         }*/

        //TODO comment code.
        @JvmName("doSomethingString")
        fun doSomething(list: List<String>): Int {
            return 0
        }

        //TODO comment code.
        @JvmName("doSomethingInt")
        fun doSomething(list: List<Int>): Int {
            return 0
        }
    }

    fun main() {
        val obj = SimpleClass()
    }

    //At runtime, we only know that we have two lists, without knowing what type the objects are from those two lists. This outcome is clear from the error that we get:
    //Exception in thread "main" java.lang.ClassFormatError: Duplicate method name "doSomething" with signature "(Ljava.util.List;)I" in class file SimpleClass

    //When writing our code, it’s worth keeping in mind that type erasure will happen at compile time. If you would really want to do something like we did in the above code, you’d need to use the @JvmName annotation on our methods:

}