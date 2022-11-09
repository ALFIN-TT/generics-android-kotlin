package com.example.generics.example.examplethree

class IntStack {
    private val elements: MutableList<Int> = ArrayList()

    fun pop(): Int {
        return elements.removeLast()
    }

    fun push(value: Int) {
        elements.add(value)
    }
}


class Stack<T> {
    private val elements: MutableList<T> = ArrayList()

    fun pop(): T {
        return elements.removeLast()
    }

    fun push(value: T) {
        elements.add(value)
    }
}


open class Mammal {
    fun move() {}
}

class Cat : Mammal()
class Dog : Mammal()

class PetOwner<T>


open class User(val point: Int)
class Moderator : User(5)
class ChatMember : User(1)




open class Student(open val age: Int) : Comparable<Student> {

    override fun compareTo(other: Student): Int {
        return if (this.age > other.age) 1 else 0
    }
}

class Boy(override val age: Int) : Student(age)
class Girl(override val age: Int) : Student(age)
