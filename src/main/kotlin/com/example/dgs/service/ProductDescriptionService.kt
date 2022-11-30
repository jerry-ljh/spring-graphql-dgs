package com.example.dgs.service

import com.example.dgs.ID
import com.example.dgs.generated.types.ProductDescription
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductDescriptionService {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getProductDescription(productId: Long): ProductDescription {
        return ProductDescription(description = { "$productId 상품 설명 " })
    }

    fun getProductDescriptionMap(productIds: Collection<ID>): Map<ID, ProductDescription> {
        log.info("find description in batch")
        return productIds.associateWith { productId -> getProductDescription(productId.toLong()) }
    }
}