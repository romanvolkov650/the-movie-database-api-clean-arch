package com.romanvolkov.themovie.data.repository

import com.romanvolkov.themovie.data.api.MovieApi
import com.romanvolkov.themovie.data.extensions.toMovie
import com.romanvolkov.themovie.data.extensions.toPopularResponse
import com.romanvolkov.themovie.data.extensions.toVideoResult
import com.romanvolkov.themovie.data.extensions.toVideos
import com.romanvolkov.themovie.domain.model.Movie
import com.romanvolkov.themovie.domain.model.PopularResponse
import com.romanvolkov.themovie.domain.model.Videos
import com.romanvolkov.themovie.domain.repository.MovieRepository
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getPopular(page: Int): PopularResponse {
        return api.getPopular(page = page).toPopularResponse()
    }

    override suspend fun getMovieDetail(movieId: Int): Movie {
        return api.getMovieDetail(movieId).toMovie()
    }

    override suspend fun getMovieTrailer(movieId: Int): Videos {
        return api.getMovieTrailers(movieId).toVideos()
    }
}

