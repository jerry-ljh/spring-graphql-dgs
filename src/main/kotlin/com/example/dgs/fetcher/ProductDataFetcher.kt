package com.example.dgs.fetcher

import com.example.dgs.ID
import com.example.dgs.dataloader.ProductDescriptionDataloader
import com.example.dgs.dataloader.ProductOptionDataloader
import com.example.dgs.dataloader.ShopDataloader
import com.example.dgs.dto.ProductResponse
import com.example.dgs.generated.DgsConstants
import com.example.dgs.generated.types.ProductDescription
import com.example.dgs.generated.types.ProductOption
import com.example.dgs.generated.types.Shop
import com.example.dgs.service.ProductService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import org.dataloader.DataLoader
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

@DgsComponent
class ProductDataFetcher(
    private val productService: ProductService
) {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    @DgsData(parentType = DgsConstants.PRODUCT_LIST.TYPE_NAME, field = DgsConstants.PRODUCT_LIST.Total_count)
    fun totalCountLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<Int> {
        val totalCount = 50
        return CompletableFuture.supplyAsync {
            log.info("find total count")
            totalCount
        }
    }

    @DgsData(parentType = DgsConstants.PRODUCT_LIST.TYPE_NAME, field = DgsConstants.PRODUCT_LIST.Item_list)
    fun productsLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<ProductResponse>> {
        val input = dfe.executionStepInfo.parent.arguments["idList"] as List<ID>
        return CompletableFuture.supplyAsync { productService.getProductWithDataLoaderMap(input).values.toList() }
    }

    @DgsData(parentType = DgsConstants.PRODUCT.TYPE_NAME, field = DgsConstants.PRODUCT.Shop)
    fun shopLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<Shop> {
        val loader: DataLoader<ID, Shop> = dfe.getDataLoader(ShopDataloader::class.java)
        val product: ProductResponse = dfe.getSource()
        if (product.isShopInitialized()) {
            return CompletableFuture<Shop>().completeAsync { product.shop }
        }
        return loader.load(product.id)
    }

    @DgsData(parentType = DgsConstants.PRODUCT.TYPE_NAME, field = DgsConstants.PRODUCT.Description)
    fun descriptionLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<ProductDescription> {
        val loader: DataLoader<ID, ProductDescription> = dfe.getDataLoader(ProductDescriptionDataloader::class.java)
        val product: ProductResponse = dfe.getSource()
        if (product.isDescriptionInitialized()) {
            return CompletableFuture<ProductDescription>().completeAsync { product.description }
        }
        return loader.load(product.id)
    }


    @DgsData(parentType = DgsConstants.PRODUCT.TYPE_NAME, field = DgsConstants.PRODUCT.Option)
    fun optionLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<ProductOption>> {
        val loader: DataLoader<ID, List<ProductOption>> = dfe.getDataLoader(ProductOptionDataloader::class.java)
        val product: ProductResponse = dfe.getSource()
        if (product.isOptionInitialized()) {
            return CompletableFuture<List<ProductOption>>().completeAsync { product.option }
        }
        return loader.load(product.id)
    }
}