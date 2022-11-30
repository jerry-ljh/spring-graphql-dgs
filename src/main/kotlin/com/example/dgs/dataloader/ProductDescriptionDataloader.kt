package com.example.dgs.dataloader

import com.example.dgs.ID
import com.example.dgs.generated.types.ProductDescription
import com.example.dgs.service.ProductDescriptionService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "productDescription")
class ProductDescriptionDataloader(
    private val productDescriptionService: ProductDescriptionService
) : MappedBatchLoader<String, ProductDescription> {

    override fun load(productIdSet: Set<ID>): CompletionStage<Map<String, ProductDescription>> {
        return CompletableFuture.supplyAsync { productDescriptionService.getProductDescriptionMap(productIdSet) }
    }
}