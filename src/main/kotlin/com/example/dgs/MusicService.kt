package com.example.dgs

import com.example.dgs.generated.types.Music
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class MusicService {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getMusic(title: String): Music {
        log.info("find music title: $title")
        return Music(name = { "$title music" })
    }

    fun getMusicMapByTitle(titles: Collection<String>): Map<String, Music> {
        log.info("find musics titles: $titles")
        return titles.associateWith { title -> Music(name = { "$title music" }) }
    }
}