package com.example.dgs.resolver

import com.example.dgs.generated.client.ProductGraphQLQuery
import com.example.dgs.generated.client.ProductProjectionRoot
import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.client.codegen.GraphQLQueryRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ProductQueryResolverTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor


    @Test
    fun `product 조회 테스트1`() {
        // given
        val input = mapOf("id" to 1)
        // when
        val result = dgsQueryExecutor.execute(
            """
            query {
              product(id: "1"){
                id
                name
                shop{
                  id
                  name
                }
                description {
                  text
                }
                option {
                  key
                  value
                }
              }
            }
        """.trimIndent(), input
        )
        // then
        Assertions.assertThat(result.errors).isEmpty()
    }

    @Test
    fun `product 조회 테스트2`() {
        // given
        val graphQLQueryRequest = GraphQLQueryRequest(
            ProductGraphQLQuery.Builder()
                .id("1")
                .build(),
            ProductProjectionRoot()
                .id()
                .name()
                .shop().id().name().root
                .description().text().root
                .option().key().value()
        )
        // when
        val result = dgsQueryExecutor.execute(graphQLQueryRequest.serialize())
        // then
        Assertions.assertThat(result.errors).isEmpty()
    }
}