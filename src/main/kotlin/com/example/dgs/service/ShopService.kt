package com.example.dgs.service

import com.example.dgs.ID
import com.example.dgs.generated.types.Shop
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class ShopService {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    fun getShop(productId: Long): Shop {
        log.info("find shop $productId")

        return Shop(id = "${System.currentTimeMillis()}", name = "shop_${productId % 3}")
    }

    fun getShopMapByProductId(productIds: Collection<ID>): Map<ID, Shop> {
        log.info("find shops in batch $productIds")
        return productIds.associateWith { id ->
            Shop(
                id = "${System.currentTimeMillis()}",
                name = "shop_${id.toLong() % 3}"
            )
        }
    }
}