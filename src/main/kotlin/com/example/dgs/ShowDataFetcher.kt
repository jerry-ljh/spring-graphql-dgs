package com.example.dgs

import com.example.dgs.generated.DgsConstants
import com.example.dgs.generated.types.Actor
import com.example.dgs.generated.types.Location
import com.example.dgs.generated.types.Music
import com.example.dgs.generated.types.Show
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import org.dataloader.DataLoader
import java.util.concurrent.CompletableFuture


@DgsComponent
class ShowsDataFetcher(private val actorService: ActorService) {
    private val shows = listOf(
        Show(title = { "Stranger Things" }),
        Show(title = { "Ozark" }),
        Show(title = { "The Crown" }),
        Show(title = { "Dead to Me" }),
        Show(title = { "Orange is the New Black" })
    )

    /*
    @DgsQuery(field = DgsConstants.QUERY.Shows)
    fun shows(): List<Show> {
        val titleList = shows.map { it.title }
        val actorListMapByTitle: Map<String, List<Actor>> = actorService.getActorListMapByTitle(titleList)
        return actorListMapByTitle.map { (title, actorList) -> Show(title, actorList) }
    }
    */

    // 전체 쿼리 필드중 일부만 조회해서 가져옴
    @DgsQuery(field = DgsConstants.QUERY.Shows)
    fun shows(): List<Show> {
        return shows
    }


    /* 쿼리 필드에 따른 datafetch
    @DgsData(parentType = DgsConstants.SHOW.TYPE_NAME)
    fun actors(dfe: DgsDataFetchingEnvironment): List<Actor> {
        val show: Show = dfe.getSource()
        return actorService.getActorList(show.title)
    }
     */

    // dataloader를 이용한 batch loader
    @DgsData(parentType = DgsConstants.SHOW.TYPE_NAME)
    fun actors(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Actor>> {
        val dataloader: DataLoader<String, List<Actor>> = dfe.getDataLoader(ActorDataloader::class.java)
        val show: Show = dfe.getSource()
        return dataloader.load(show.title)
    }

    @DgsData(parentType = DgsConstants.SHOW.TYPE_NAME)
    fun music(dfe: DgsDataFetchingEnvironment): CompletableFuture<Music> {
        val dataloader: DataLoader<String, Music> = dfe.getDataLoader(MusicDataloader::class.java)
        val show: Show = dfe.getSource()
        return dataloader.load(show.title)
    }

    @DgsData(parentType = DgsConstants.SHOW.TYPE_NAME)
    fun location(dfe: DgsDataFetchingEnvironment): CompletableFuture<Location> {
        val dataloader: DataLoader<String, Location> = dfe.getDataLoader(LocationDataloader::class.java)
        val show: Show = dfe.getSource()
        return dataloader.load(show.title)
    }
}