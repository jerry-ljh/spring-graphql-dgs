package com.example.dgs.service

import com.example.dgs.ID
import com.example.dgs.dataloader.ProductWithDataLoader
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productDescriptionService: ProductDescriptionService,
    private val productOptionService: ProductOptionService,
    private val shopService: ShopService
) {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getProduct(id: Long): ProductWithDataLoader {
        val description = productDescriptionService.getProductDescription(id)
        val option = productOptionService.getProductOptionList(id)
        val shop = shopService.getShop(id)
        return ProductWithDataLoader(
            id = id.toString(),
            name = "test product_$id",
            description = { description },
            option = { option },
            shop = { shop }
        )
    }

    fun getProductWithDataLoader(id: Long): ProductWithDataLoader {
        log.info("find product $id")
        return ProductWithDataLoader(id = id.toString(), name = "test product_$id")
    }

    fun getProductWithDataLoaderMap(ids: Collection<ID>): Map<ID, ProductWithDataLoader> {
        log.info("find product in batch $ids")
        return ids.associateWith { id -> ProductWithDataLoader(id = id, name = "test product_$id") }
    }
}