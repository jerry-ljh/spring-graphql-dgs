package com.example.dgs.configuration

import com.example.dgs.toFuture
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException
import com.netflix.graphql.types.errors.TypedGraphQLError
import graphql.GraphQLError
import graphql.execution.DataFetcherExceptionHandler
import graphql.execution.DataFetcherExceptionHandlerParameters
import graphql.execution.DataFetcherExceptionHandlerResult
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.concurrent.CompletableFuture

@Component
class DataFetchingExceptionHandler : DataFetcherExceptionHandler {

    private val log = LoggerFactory.getLogger(this::class.simpleName)

    override fun handleException(parameters: DataFetcherExceptionHandlerParameters): CompletableFuture<DataFetcherExceptionHandlerResult> {
        log.error("path : ${parameters.path}, args ${parameters.argumentValues}, ${extractException(parameters)}")
        return DataFetcherExceptionHandlerResult.newResult()
            .error(createTypedGraphQLError(parameters))
            .build()
            .toFuture()
    }

    private fun createTypedGraphQLError(parameters: DataFetcherExceptionHandlerParameters): GraphQLError {
        val exception = extractException(parameters)
        return builder(exception)
            .path(parameters.path)
            .message("${exception.javaClass.simpleName}: ${exception.message}")
            .build()
    }

    private fun extractException(parameters: DataFetcherExceptionHandlerParameters): Throwable {
        if (parameters.exception.cause == null) {
            return parameters.exception
        }
        return parameters.exception.cause!!
    }

    private fun builder(exception: Throwable): TypedGraphQLError.Builder {
        return when (exception) {
            is DgsEntityNotFoundException -> TypedGraphQLError.newNotFoundBuilder()
            is DgsBadRequestException -> TypedGraphQLError.newBadRequestBuilder()
            else -> TypedGraphQLError.newInternalErrorBuilder()
        }
    }
}
