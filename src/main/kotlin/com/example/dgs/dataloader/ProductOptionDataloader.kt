package com.example.dgs.dataloader

import com.example.dgs.ID
import com.example.dgs.generated.types.ProductOption
import com.example.dgs.service.ProductOptionService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "productOption")
class ProductOptionDataloader(
    private val productOptionService: ProductOptionService
) : MappedBatchLoader<String, List<ProductOption>> {

    override fun load(productIdSet: Set<ID>): CompletionStage<Map<String, List<ProductOption>>> {
        return CompletableFuture.supplyAsync { productOptionService.getProductOptionMap(productIdSet) }
    }
}