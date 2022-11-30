package com.example.dgs.resolver

import com.example.dgs.ID
import com.example.dgs.generated.DgsConstants
import com.example.dgs.generated.types.Product
import com.example.dgs.generated.types.ProductList
import com.example.dgs.service.ProductService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument

@DgsComponent
class ProductResolver(private val productService: ProductService) {

    @DgsQuery(field = DgsConstants.QUERY.Products)
    fun products(@InputArgument idList: List<ID>): ProductList {
        return ProductList()
    }

    @DgsQuery(field = DgsConstants.QUERY.Product)
    fun product(@InputArgument id: ID): Product {
        return productService.getProduct(id.toLong())
    }
}