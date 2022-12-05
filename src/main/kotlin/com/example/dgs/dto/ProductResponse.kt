package com.example.dgs.dto

import com.example.dgs.generated.types.ProductDescription
import com.example.dgs.generated.types.ProductOption
import com.example.dgs.generated.types.Shop
import com.fasterxml.jackson.annotation.JsonProperty

data class ProductResponse(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,
) {
    @JsonProperty("shop")
    lateinit var shop: Shop

    @JsonProperty("description")
    lateinit var description: ProductDescription

    @JsonProperty("option")
    lateinit var option: List<ProductOption>

    fun isShopInitialized() = ::shop.isInitialized

    fun isDescriptionInitialized() = ::description.isInitialized

    fun isOptionInitialized() = ::option.isInitialized
}