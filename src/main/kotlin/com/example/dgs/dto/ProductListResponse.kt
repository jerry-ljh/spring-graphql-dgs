package com.example.dgs.dto

import com.fasterxml.jackson.annotation.JsonProperty

class ProductListResponse(
    @JsonProperty("total_count")
    var totalCount: Int,
    @JsonProperty("item_list")
    var itemList: List<ProductResponse>
)
