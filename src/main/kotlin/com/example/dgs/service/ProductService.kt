package com.example.dgs.service

import com.example.dgs.ID
import com.example.dgs.dto.ProductResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val productDescriptionService: ProductDescriptionService,
    private val productOptionService: ProductOptionService,
    private val shopService: ShopService
) {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getProduct(id: Long): ProductResponse {
        return ProductResponse(id = id.toString(), name = "test product_$id")
            .apply {
                description = productDescriptionService.getProductDescription(id)
                option = productOptionService.getProductOptionList(id)
                shop = shopService.getShop(id)
            }
    }

    fun getProductWithDataLoader(id: Long): ProductResponse {
        log.info("find product $id")
        return ProductResponse(id = id.toString(), name = "test product_$id")
    }

    fun getProductWithDataLoaderMap(ids: Collection<ID>): Map<ID, ProductResponse> {
        log.info("find product in batch $ids")
        return ids.associateWith { id -> ProductResponse(id = id, name = "test product_$id") }
    }

    fun countProduct(): Int {
        log.info("find product count")
        return 100
    }
}