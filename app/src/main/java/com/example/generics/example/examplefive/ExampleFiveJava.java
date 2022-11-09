package com.example.generics.example.examplefive;

public class ExampleFiveJava {

    static void covariantExampleWithRuntimeCrash() {
        Integer[] ints = {1, 2, 3, 4, 5};
        Object[] objects = ints;
        objects[2] = "ABCD";
    }


    void main() {
        covariantExampleWithRuntimeCrash();
    }
}

