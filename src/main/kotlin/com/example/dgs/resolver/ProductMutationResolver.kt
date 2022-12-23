package com.example.dgs.resolver

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import org.slf4j.LoggerFactory

@DgsComponent
class ProductMutationResolver {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    @DgsMutation
    fun saveProductMetadata(@InputArgument metadata: String): String {
        log.info("save metadata: $metadata")
        return metadata
    }
}