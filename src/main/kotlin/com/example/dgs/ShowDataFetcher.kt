package com.example.dgs

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import org.dataloader.DataLoader
import java.util.concurrent.CompletableFuture


@DgsComponent
class ShowsDataFetcher(private val actorService: ActorService) {
    private val shows = listOf(
        Show("Stranger Things"),
        Show("Ozark"),
        Show("The Crown"),
        Show("Dead to Me"),
        Show("Orange is the New Black")
    )

    /*
    @DgsQuery
    fun shows(): List<Show> {
        val titleList = shows.map { it.title }
        val actorListMapByTitle: Map<String, List<Actor>> = actorService.getActorListMapByTitle(titleList)
        return actorListMapByTitle.map { (title, actorList) -> Show(title, actorList) }
    }
    */

    // 전체 쿼리 필드중 일부만 조회해서 가져옴
    @DgsQuery
    fun shows(): List<Show> {
        return shows
    }


    /* 쿼리 필드에 따른 datafetch
    @DgsData(parentType = "Show")
    fun actors(dfe: DgsDataFetchingEnvironment): List<Actor> {
        val show: Show = dfe.getSource()
        return actorService.getActorList(show.title)
    }
     */

    // dataloader를 이용한 batch loader
    @DgsData(parentType = "Show")
    fun actors(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Actor>> {
        val dataloader: DataLoader<String, List<Actor>> = dfe.getDataLoader(ActorDataloader::class.java)
        val show: Show = dfe.getSource()
        return dataloader.load(show.title)
    }

    data class Show(val title: String, val actors: List<Actor> = emptyList())
}