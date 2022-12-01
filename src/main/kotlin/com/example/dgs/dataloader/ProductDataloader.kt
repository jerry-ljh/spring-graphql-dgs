package com.example.dgs.dataloader

import com.example.dgs.ID
import com.example.dgs.generated.types.ProductDescription
import com.example.dgs.generated.types.ProductOption
import com.example.dgs.generated.types.Shop
import com.example.dgs.service.ProductService
import com.example.dgs.throwFieldInitException
import com.fasterxml.jackson.annotation.JsonProperty
import com.netflix.graphql.dgs.DgsDataLoader
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage

@DgsDataLoader(name = "product")
class ProductDataloader(private val productService: ProductService) : MappedBatchLoader<String, ProductWithDataLoader> {

    override fun load(productIdSet: Set<ID>): CompletionStage<Map<String, ProductWithDataLoader>> {
        return CompletableFuture.supplyAsync { productService.getProductWithDataLoaderMap(productIdSet) }
    }
}

data class ProductListWithDataLoader(
    @JsonProperty("total_count")
    val total_count: () -> Int = throwFieldInitException("total_count"),
    @JsonProperty("item_list")
    val item_list: () -> List<ProductWithDataLoader> = throwFieldInitException("item_list"),
)

data class ProductWithDataLoader(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("shop")
    val shop: () -> Shop = throwFieldInitException("shop"),
    @JsonProperty("description")
    val description: () -> ProductDescription = throwFieldInitException("description"),
    @JsonProperty("option")
    val option: () -> List<ProductOption> = throwFieldInitException("option")
)