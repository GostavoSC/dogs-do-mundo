package com.example.dogsdomundo.data.model

class Dog(
    val name: String,
    val image: String
) : Comparable<Dog> {
    override fun compareTo(other: Dog): Int {
        return name.compareTo(other.name)
    }
}
