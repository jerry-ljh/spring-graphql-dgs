package com.example.dgs.service

import com.example.dgs.ID
import com.example.dgs.generated.types.Product
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ProductService {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getProduct(id: Long): Product {
        return Product(id = { id.toString() }, name = { "test product_$id" })
    }

    fun getProductMap(ids: Collection<ID>): Map<ID, Product> {
        log.info("find product in batch")
        return ids.associateWith { id -> getProduct(id.toLong()) }
    }
}