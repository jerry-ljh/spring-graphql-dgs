package com.example.dgs.configuration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.netflix.graphql.dgs.DgsScalar
import graphql.language.StringValue
import graphql.schema.Coercing
import graphql.schema.CoercingParseLiteralException

@DgsScalar(name = "Json")
class JsonCoercing : Coercing<String, String> {

    private val objectMapper = jacksonObjectMapper()

    override fun serialize(dataFetcherResult: Any): String {
        return dataFetcherResult.toString()
    }

    override fun parseValue(input: Any): String {
        input as String
        validate(input)
        return input
    }

    override fun parseLiteral(input: Any): String {
        val json = (input as StringValue).value
        validate(json)
        return json
    }

    private fun validate(value: String) {
        try {
            objectMapper.readTree(value)
        } catch (e: Exception) {
            throw CoercingParseLiteralException("Invalid Json. value = $value", e)
        }
    }
}