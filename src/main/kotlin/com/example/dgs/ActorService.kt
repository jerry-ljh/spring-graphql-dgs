package com.example.dgs

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ActorService {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getActorList(title: String): List<Actor> {
        log.info("find actors by title : $title")
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

data class Actor(val name: String, val age: Int)
