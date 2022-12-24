package com.example.dgs.configuration

import com.netflix.graphql.dgs.client.GraphQLClient
import com.netflix.graphql.dgs.client.GraphQLResponse
import com.netflix.graphql.dgs.client.HttpResponse
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.web.client.RestTemplate

class ProductGraphQLClient(
    private val url: String,
    private val restTemplate: RestTemplate
) {

    fun request(graphQLQueryRequest: ProductGraphQLQueryRequest): GraphQLResponse {
        val operationName = graphQLQueryRequest.query.getOperationName()
        val client = GraphQLClient.createCustom("$url/$operationName") { url, headers, body ->
            val httpHeaders = HttpHeaders()
            headers.forEach { httpHeaders.addAll(it.key, it.value) }
            val exchange = restTemplate.postForEntity(url, HttpEntity(body, httpHeaders), String::class.java)
            HttpResponse(exchange.statusCodeValue, exchange.body)
        }
        return client.executeQuery(graphQLQueryRequest.serialize(), mapOf())
    }
}