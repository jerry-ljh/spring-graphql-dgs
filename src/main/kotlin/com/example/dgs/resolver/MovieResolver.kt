package com.example.dgs.resolver

import com.example.dgs.generated.DgsConstants
import com.example.dgs.generated.types.Movie
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class MovieResolver {
    
    private val movies = listOf(
        Movie(title = { "Stranger Things" }),
        Movie(title = { "Ozark" }),
        Movie(title = { "The Crown" }),
        Movie(title = { "Dead to Me" }),
        Movie(title = { "Orange is the New Black" })
    )

    @DgsQuery(field = DgsConstants.QUERY.Movies)
    fun movies(): List<Movie> {
        return movies
    }

}