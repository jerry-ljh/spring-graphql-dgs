package com.example.dgs

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery


@DgsComponent
class ShowsDataFetcher(
    private val actorService: ActorService
) {
    private val shows = listOf(
        Show("Stranger Things"),
        Show("Ozark"),
        Show("The Crown"),
        Show("Dead to Me"),
        Show("Orange is the New Black")
    )

    @DgsQuery
    fun shows(): List<Show> {
        return shows
    }

    @DgsData(parentType = "Show")
    fun actors(dfe: DgsDataFetchingEnvironment): List<Actor> {
        val show: Show = dfe.getSource()
        return actorService.getActorList(show.title)
    }

    data class Show(val title: String, val actors: List<Actor> = emptyList())
}