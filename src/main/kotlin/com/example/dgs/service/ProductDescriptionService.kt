package com.example.dgs.service

import com.example.dgs.ID
import com.example.dgs.generated.types.ProductDescription
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductDescriptionService {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getProductDescription(productId: Long): ProductDescription {
        log.info("find description $productId")
        return ProductDescription(text = "$productId 상품 설명 ")
    }

    fun getProductDescriptionMap(productIds: Collection<ID>): Map<ID, ProductDescription> {
        log.info("find description in batch $productIds")
        return productIds.associateWith { productId -> ProductDescription(text = "$productId 상품 설명 ") }
    }
}