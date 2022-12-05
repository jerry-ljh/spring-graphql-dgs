package com.example.dgs.dataloader

import com.example.dgs.ID
import com.example.dgs.dto.ProductResponse
import com.example.dgs.service.ProductService
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "product")
class ProductDataloader(private val productService: ProductService) : MappedBatchLoader<ID, ProductResponse> {

    override fun load(productIdSet: Set<ID>): CompletionStage<Map<ID, ProductResponse>> {
        return CompletableFuture.supplyAsync { productService.getProductWithDataLoaderMap(productIdSet) }
    }
}

