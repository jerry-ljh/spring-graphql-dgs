package com.example.dgs

import com.example.dgs.generated.types.Actor
import org.springframework.stereotype.Repository

@Repository
class ActorRepository {

    fun getActorList(title: String): List<Actor> {
        return when (title) {
            "Stranger Things" -> listOf(Actor(name = { "jerry" }, age = { 27 }))
            "Ozark" -> listOf(Actor(name = { "tom" }, age = { 27 }))
            "The Crown" -> listOf(Actor(name = { "ben" }, age = { 27 }))
            "Dead to Me" -> listOf(Actor(name = { "amy" }, age = { 27 }))
            "Orange is the New Black" -> listOf(Actor(name = { "don" }, age = { 27 }))
            else -> emptyList()
        }
    }
}

