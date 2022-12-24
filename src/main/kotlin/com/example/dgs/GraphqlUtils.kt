package com.example.dgs

import com.netflix.graphql.dgs.client.codegen.GraphQLQuery
import java.util.concurrent.CompletableFuture

typealias ID = String

fun <T> T.toFuture(): CompletableFuture<T> = CompletableFuture.completedFuture(this)

fun GraphQLQuery.getProductOperationName() = "Product__${this.getOperationName()}"
