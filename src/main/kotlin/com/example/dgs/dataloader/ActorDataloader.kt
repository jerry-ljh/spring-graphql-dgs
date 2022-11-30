package com.example.dgs.dataloader

import com.example.dgs.generated.types.Actor
import com.example.dgs.service.ActorService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "actors")
class ActorDataloader(private val actorService: ActorService) : MappedBatchLoader<String, List<Actor>> {

    override fun load(titleSet: Set<String>): CompletionStage<Map<String, List<Actor>>> {
        return CompletableFuture.supplyAsync { actorService.getActorListMapByTitle(titleSet) }
    }
}