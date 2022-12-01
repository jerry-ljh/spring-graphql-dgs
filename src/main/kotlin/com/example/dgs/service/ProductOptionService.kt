package com.example.dgs.service

import com.example.dgs.ID
import com.example.dgs.generated.types.ProductOption
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductOptionService {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getProductOptionList(productId: Long): List<ProductOption> {
        log.info("find option $productId")
        return listOf(
            ProductOption(key = "size", value = "S"),
            ProductOption(key = "size", value = "M"),
            ProductOption(key = "size", value = "L"),
        )
    }

    fun getProductOptionMap(productIds: Collection<ID>): Map<ID, List<ProductOption>> {
        log.info("find option in batch$productIds")
        return productIds.associateWith {
            listOf(
                ProductOption(key = "size", value = "S"),
                ProductOption(key = "size", value = "M"),
                ProductOption(key = "size", value = "L"),
            )
        }
    }
}