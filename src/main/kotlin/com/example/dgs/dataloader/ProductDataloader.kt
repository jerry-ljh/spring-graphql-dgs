package com.example.dgs.dataloader

import com.example.dgs.ID
import com.example.dgs.generated.types.Product
import com.example.dgs.service.ProductService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "product")
class ProductDataloader(private val productService: ProductService) : MappedBatchLoader<String, Product> {

    override fun load(productIdSet: Set<ID>): CompletionStage<Map<String, Product>> {
        return CompletableFuture.supplyAsync { productService.getProductMap(productIdSet) }
    }
}