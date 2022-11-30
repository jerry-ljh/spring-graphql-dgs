package com.example.dgs.fetcher

import com.example.dgs.dataloader.ActorDataloader
import com.example.dgs.dataloader.LocationDataloader
import com.example.dgs.dataloader.MusicDataloader
import com.example.dgs.generated.DgsConstants
import com.example.dgs.generated.types.Actor
import com.example.dgs.generated.types.Location
import com.example.dgs.generated.types.Music
import com.example.dgs.generated.types.Show
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import org.dataloader.DataLoader
import java.util.concurrent.CompletableFuture


@DgsComponent
class ShowsDataFetcher {

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
    fun location(dfe: DgsDataFetchingEnvironment): CompletableFuture<Location?> {
        val dataloader: DataLoader<String, Location?> = dfe.getDataLoader(LocationDataloader::class.java)
        val show: Show = dfe.getSource()
        return dataloader.load(show.title)
    }
}