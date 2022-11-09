package com.example.generics.example.examplefive


interface Any
interface Wild : Any
open class Animal : Any

open class Dog : Animal(), Wild
class Human : Animal()

class Puppy : Dog()
class Hund : Dog()
