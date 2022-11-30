package com.example.dgs.dataloader

import com.example.dgs.ID
import com.example.dgs.generated.types.Shop
import com.example.dgs.service.ShopService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "shop")
class ShopDataloader(private val shopService: ShopService) : MappedBatchLoader<String, Shop> {

    override fun load(productIdSet: Set<ID>): CompletionStage<Map<String, Shop>> {
        return CompletableFuture.supplyAsync { shopService.getShopMapByProductId(productIdSet) }
    }
}