package com.example.dgs.service

import com.example.dgs.generated.types.Location
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class LocationService {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getLocationMapByTitle(titles: Collection<String>): Map<String, Location?> {
        log.info("find locations, titles: $titles")
        return titles.associateWith { title ->
            if (title == "The Crown") Location(name = { "A 콘서트홀" }) else null
        }
    }
}