package com.example.dgs

import org.springframework.stereotype.Repository

@Repository
class ActorRepository {

    fun getActorList(title: String): List<Actor> {
        return when (title) {
            "Stranger Things" -> listOf(Actor("jerry", 27))
            "Ozark" -> listOf(Actor("tom", 27))
            "The Crown" -> listOf(Actor("ben", 27))
            "Dead to Me" -> listOf(Actor("amy", 27))
            "Orange is the New Black" -> listOf(Actor("don", 27))
            else -> emptyList()
        }
    }
}

