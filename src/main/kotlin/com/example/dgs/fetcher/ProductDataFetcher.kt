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
import com.example.dgs.toFuture
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import org.dataloader.DataLoader
import java.util.concurrent.CompletableFuture

@DgsComponent
class ProductDataFetcher(
    private val productService: ProductService
) {

    @DgsData(parentType = DgsConstants.PRODUCT_LIST.TYPE_NAME, field = DgsConstants.PRODUCT_LIST.Total_count)
    fun totalCountLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<Int> {
        return productService.countProduct().toFuture()
    }

    @DgsData(parentType = DgsConstants.PRODUCT_LIST.TYPE_NAME, field = DgsConstants.PRODUCT_LIST.Item_list)
    fun productsLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<ProductResponse>> {
        val input = dfe.executionStepInfo.parent.arguments["idList"] as List<ID>
        return productService.getProductWithDataLoaderMap(input).values.toList().toFuture()
    }

    @DgsData(parentType = DgsConstants.PRODUCT.TYPE_NAME, field = DgsConstants.PRODUCT.Shop)
    fun shopLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<Shop> {
        val loader: DataLoader<ID, Shop> = dfe.getDataLoader(ShopDataloader::class.java)
        val product: ProductResponse = dfe.getSource()
        if (product.isShopInitialized()) {
            return product.shop.toFuture()
        }
        return loader.load(product.id)
    }

    @DgsData(parentType = DgsConstants.PRODUCT.TYPE_NAME, field = DgsConstants.PRODUCT.Description)
    fun descriptionLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<ProductDescription> {
        val loader: DataLoader<ID, ProductDescription> = dfe.getDataLoader(ProductDescriptionDataloader::class.java)
        val product: ProductResponse = dfe.getSource()
        if (product.isDescriptionInitialized()) {
            return product.description.toFuture()
        }
        return loader.load(product.id)
    }


    @DgsData(parentType = DgsConstants.PRODUCT.TYPE_NAME, field = DgsConstants.PRODUCT.Option)
    fun optionLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<ProductOption>> {
        val loader: DataLoader<ID, List<ProductOption>> = dfe.getDataLoader(ProductOptionDataloader::class.java)
        val product: ProductResponse = dfe.getSource()
        if (product.isOptionInitialized()) {
            return product.option.toFuture()
        }
        return loader.load(product.id)
    }
}