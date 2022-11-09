package com.example.generics.data

object HtmlData {
    const val FIRST_TEXT =
        "<P style=\"font-family:verdana\">Imagine you are managing a zoo or an exotic pet shop so that you have to organize and keep a reference of all animal species available.</P> <P style=\"font-family:verdana\">You will most likely put each animal in a cage and declare several attributes and actions associated.</P>"
    const val CREATE_CAGE_FOR_SPECIFIC_ANIMAL = "\n" +
            "<P style=\"font-family:verdana\"> For instance, for a dog\n" +
            "<PRE>\n" +
            "<CODE>\n" +
            "//define a cage\n" +
            "class Cage(var dog: Dog, val size: Double)\n" +
            "//add a dog.\n" +
            "val dog = Dog(id = 0, name = \"Doglin\", furColor = FurColor.BLACK)\n" +
            "//create a cage for dog.\n" +
            "val cage = Cage(dog = dog, size = 6.0)\n" +
            "//In this case cage can be use only for dog.\n" +
            "//means this cage cannot use for cat.\n" +
            "<PRE>\n" +
            "<CODE>\n" +
            "</P>"
}