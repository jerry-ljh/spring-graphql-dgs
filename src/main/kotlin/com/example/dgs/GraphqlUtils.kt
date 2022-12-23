package com.example.dgs

import java.util.concurrent.CompletableFuture

typealias ID = String

fun <T> T.toFuture(): CompletableFuture<T> = CompletableFuture.completedFuture(this)

