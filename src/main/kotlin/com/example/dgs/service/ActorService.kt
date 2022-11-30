package com.example.dgs.service

import com.example.dgs.generated.types.Actor
import com.example.dgs.repository.ActorRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ActorService(
    private val actorRepository: ActorRepository
) {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getActorListMapByTitle(titles: Collection<String>): Map<String, List<Actor>> {
        log.info("find actors by titleList : $titles")
        return titles.associateWith { title -> actorRepository.getActorList(title) }
    }

    fun getActorList(title: String): List<Actor> {
        log.info("find actors by title : $title")
        return actorRepository.getActorList(title)
    }
}
