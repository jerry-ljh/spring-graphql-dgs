package com.example.dgs

import com.example.dgs.generated.types.Location
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "location")
class LocationDataloader(private val locationService: LocationService) : MappedBatchLoader<String, Location?> {

    override fun load(titleSet: Set<String>): CompletionStage<Map<String, Location?>> {
        return CompletableFuture.supplyAsync { locationService.getLocationMapByTitle(titleSet) }
    }
}