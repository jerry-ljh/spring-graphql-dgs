package com.example.dgs.resolver

import com.example.dgs.ID
import com.example.dgs.dto.ProductListResponse
import com.example.dgs.dto.ProductResponse
import com.example.dgs.generated.DgsConstants
import com.example.dgs.service.ProductService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import org.slf4j.LoggerFactory

@DgsComponent
class ProductQueryResolver(private val productService: ProductService) {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    @DgsQuery(field = DgsConstants.QUERY.Product)
    fun product(@InputArgument id: ID): ProductResponse {
        log.info("request product")
        return productService.getProduct(id.toLong())
    }

    @DgsQuery(field = DgsConstants.QUERY.ProductWithDataLoader)
    fun productWithDatLoader(@InputArgument id: ID): ProductResponse {
        log.info("request productWithDatLoader")
        return productService.getProductWithDataLoader(id.toLong())
    }

    @DgsQuery(field = DgsConstants.QUERY.ProductsWithDataLoader)
    fun productsWithDatLoader(@InputArgument idList: List<ID>): ProductListResponse {
        log.info("request productsWithDatLoader")
        return ProductListResponse(totalCount = 0, itemList = emptyList())
    }
}