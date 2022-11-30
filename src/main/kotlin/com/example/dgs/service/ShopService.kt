package com.example.dgs.service

import com.example.dgs.ID
import com.example.dgs.generated.types.Shop
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ShopService {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getShop(id: Long): Shop {
        return Shop(id = { "${System.currentTimeMillis()}" }, name = { "shop_$id" })
    }

    fun getShopMapByProductId(productIds: Collection<ID>): Map<ID, Shop> {
        log.info("find shops in batch")
        return productIds.associateWith { id -> getShop(id.toLong()) }
    }
}