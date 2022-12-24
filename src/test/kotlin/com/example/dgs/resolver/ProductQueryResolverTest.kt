package com.example.dgs.resolver

import com.example.dgs.configuration.ProductGraphQLClient
import com.example.dgs.configuration.ProductGraphQLQueryRequest
import com.example.dgs.generated.client.ProductGraphQLQuery
import com.example.dgs.generated.client.ProductProjectionRoot
import com.netflix.graphql.dgs.DgsQueryExecutor
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.client.RestTemplate


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductQueryResolverTest {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    @LocalServerPort
    private val port = 0

    lateinit var client: ProductGraphQLClient

    @BeforeEach
    fun setClient() {
        client = ProductGraphQLClient("http://localhost:$port/graphql", RestTemplate())
    }

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
        val query = ProductGraphQLQuery.Builder()
            .id("1")
            .build()
        val graphQLQueryRequest = ProductGraphQLQueryRequest(
            query,
            ProductProjectionRoot()
                .id()
                .name()
                .shop().id().name().root
                .description().text().root
                .option().key().value()
        )
        // when
        val result = client.request(graphQLQueryRequest)
        // then
        Assertions.assertThat(result.errors).isEmpty()
    }
}