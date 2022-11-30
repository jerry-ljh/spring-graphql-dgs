package com.example.dgs.fetcher

import com.example.dgs.dataloader.ActorDataloader
import com.example.dgs.dataloader.MusicDataloader
import com.example.dgs.generated.DgsConstants
import com.example.dgs.generated.types.Actor
import com.example.dgs.generated.types.Movie
import com.example.dgs.generated.types.Music
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import org.dataloader.DataLoader
import java.util.concurrent.CompletableFuture


@DgsComponent
class MovieDataFetcher {
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

    @DgsData(parentType = DgsConstants.MOVIE.TYPE_NAME)
    fun actors(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Actor>> {
        val dataloader: DataLoader<String, List<Actor>> = dfe.getDataLoader(ActorDataloader::class.java)
        val movie: Movie = dfe.getSource()
        return dataloader.load(movie.title)
    }

    @DgsData(parentType = DgsConstants.MOVIE.TYPE_NAME)
    fun music(dfe: DgsDataFetchingEnvironment): CompletableFuture<Music> {
        val dataloader: DataLoader<String, Music> = dfe.getDataLoader(MusicDataloader::class.java)
        val movie: Movie = dfe.getSource()
        return dataloader.load(movie.title)
    }
}