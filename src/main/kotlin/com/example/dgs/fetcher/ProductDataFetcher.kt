package com.example.dgs.fetcher

import com.example.dgs.ID
import com.example.dgs.dataloader.ProductDescriptionDataloader
import com.example.dgs.dataloader.ProductOptionDataloader
import com.example.dgs.dataloader.ShopDataloader
import com.example.dgs.generated.DgsConstants
import com.example.dgs.generated.types.*
import com.example.dgs.service.ProductService
import com.netflix.graphql.dgs.*
import org.dataloader.DataLoader
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

@DgsComponent
class ProductDataFetcher(
    private val productService: ProductService
) {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    @DgsQuery(field = DgsConstants.QUERY.Products)
    fun products(@InputArgument idList: List<ID>): ProductList {
        return ProductList()
    }

    @DgsQuery(field = DgsConstants.QUERY.Product)
    fun product(@InputArgument id: ID): Product {
        return productService.getProduct(id.toLong())
    }

    @DgsData(parentType = DgsConstants.PRODUCT_LIST.TYPE_NAME, field = DgsConstants.PRODUCT_LIST.Total_count)
    fun totalCountLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<Int> {
        val totalCount = 50
        return CompletableFuture.supplyAsync {
            log.info("find total count")
            totalCount
        }
    }

    @DgsData(parentType = DgsConstants.PRODUCT_LIST.TYPE_NAME, field = DgsConstants.PRODUCT_LIST.Item_list)
    fun productsLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Product>> {
        val input = dfe.executionStepInfo.parent.arguments["idList"] as List<ID>
        return CompletableFuture.supplyAsync { productService.getProductMap(input).values.toList() }
    }

    @DgsData(parentType = DgsConstants.PRODUCT.TYPE_NAME, field = DgsConstants.PRODUCT.Shop)
    fun shopLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<Shop> {
        val loader: DataLoader<String, Shop> = dfe.getDataLoader(ShopDataloader::class.java)
        val product: Product = dfe.getSource()
        return loader.load(product.id)
    }

    @DgsData(parentType = DgsConstants.PRODUCT.TYPE_NAME, field = DgsConstants.PRODUCT.Description)
    fun descriptionLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<ProductDescription> {
        val loader: DataLoader<String, ProductDescription> = dfe.getDataLoader(ProductDescriptionDataloader::class.java)
        val product: Product = dfe.getSource()
        return loader.load(product.id)
    }


    @DgsData(parentType = DgsConstants.PRODUCT.TYPE_NAME, field = DgsConstants.PRODUCT.Option)
    fun optionLoader(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<ProductOption>> {
        val loader: DataLoader<String, List<ProductOption>> = dfe.getDataLoader(ProductOptionDataloader::class.java)
        val product: Product = dfe.getSource()
        return loader.load(product.id)
    }
}