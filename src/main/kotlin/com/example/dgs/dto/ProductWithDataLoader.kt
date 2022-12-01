package com.example.dgs.dto

import com.example.dgs.generated.types.ProductDescription
import com.example.dgs.generated.types.ProductOption
import com.example.dgs.generated.types.Shop
import com.example.dgs.throwFieldInitException
import com.fasterxml.jackson.annotation.JsonProperty

data class ProductWithDataLoader(
    @JsonProperty("id")
    val id: String,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("shop")
    val shop: () -> Shop = throwFieldInitException("shop"),
    @JsonProperty("description")
    val description: () -> ProductDescription = throwFieldInitException("description"),
    @JsonProperty("option")
    val option: () -> List<ProductOption> = throwFieldInitException("option")
)