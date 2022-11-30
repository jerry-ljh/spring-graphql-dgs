package com.example.dgs.resolver

import com.example.dgs.generated.DgsConstants
import com.example.dgs.generated.types.Show
import com.example.dgs.service.ActorService
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery

@DgsComponent
class ShowResolver(private val actorService: ActorService) {

    private val shows = listOf(
        Show(title = { "Stranger Things" }),
        Show(title = { "Ozark" }),
        Show(title = { "The Crown" }),
        Show(title = { "Dead to Me" }),
        Show(title = { "Orange is the New Black" })
    )

    // 전체 쿼리 필드중 일부만 조회해서 가져옴
    @DgsQuery(field = DgsConstants.QUERY.Shows)
    fun shows(): List<Show> {
        return shows
    }

    /*
    @DgsQuery(field = DgsConstants.QUERY.Shows)
    fun shows(): List<Show> {
        val titleList = shows.map { it.title }
        val actorListMapByTitle: Map<String, List<Actor>> = actorService.getActorListMapByTitle(titleList)
        return actorListMapByTitle.map { (title, actorList) -> Show(title, actorList) }
    }
    */

}