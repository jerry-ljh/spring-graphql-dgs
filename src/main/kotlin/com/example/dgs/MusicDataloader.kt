package com.example.dgs

import com.example.dgs.generated.types.Music
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "music")
class MusicDataloader(private val musicService: MusicService) : MappedBatchLoader<String, Music> {

    override fun load(titleSet: Set<String>): CompletionStage<Map<String, Music>> {
        return CompletableFuture.supplyAsync { musicService.getMusicMapByTitle(titleSet) }
    }
}