package com.romanvolkov.themovie.domain.interactor

import com.romanvolkov.themovie.domain.model.Movie
import com.romanvolkov.themovie.domain.model.PopularResponse
import com.romanvolkov.themovie.domain.model.Videos
import com.romanvolkov.themovie.domain.repository.MovieRepository
import javax.inject.Inject

interface MovieInteractor {
    suspend fun getPopular(
        page: Int,
        onError: (error: Throwable) -> Unit,
        onSuccess: (PopularResponse) -> Unit
    )

    suspend fun getMovieDetail(
        movieId: Int,
        onError: (error: Throwable) -> Unit,
        onSuccess: (Movie) -> Unit
    )

    suspend fun getMovieTrailer(
        movieId: Int,
        onError: (error: Throwable) -> Unit,
        onSuccess: (Videos) -> Unit
    )

    class Base @Inject constructor(private val repository: MovieRepository) : MovieInteractor {

        override suspend fun getPopular(
            page: Int,
            onError: (error: Throwable) -> Unit,
            onSuccess: (PopularResponse) -> Unit
        ) {
            kotlin.runCatching {
                val popular = repository.getPopular(page)
                onSuccess(popular)
            }.onFailure {
                onError(it)
            }
        }

        override suspend fun getMovieDetail(
            movieId: Int,
            onError: (error: Throwable) -> Unit,
            onSuccess: (Movie) -> Unit
        ) {
            kotlin.runCatching {
                val popular = repository.getMovieDetail(movieId)
                onSuccess(popular)
            }.onFailure {
                onError(it)
            }
        }

        override suspend fun getMovieTrailer(
            movieId: Int,
            onError: (error: Throwable) -> Unit,
            onSuccess: (Videos) -> Unit
        ) {
            kotlin.runCatching {
                val trailers = repository.getMovieTrailer(movieId)
                onSuccess(trailers)
            }.onFailure {
                onError(it)
            }
        }
    }
}