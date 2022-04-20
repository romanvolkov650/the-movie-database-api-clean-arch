package com.romanvolkov.themovie.domain.repository

import com.romanvolkov.themovie.domain.model.Movie
import com.romanvolkov.themovie.domain.model.PopularResponse
import com.romanvolkov.themovie.domain.model.Videos

interface MovieRepository {
    suspend fun getPopular(page: Int = 1): PopularResponse
    suspend fun getMovieDetail(movieId: Int): Movie
    suspend fun getMovieTrailer(movieId: Int): Videos
}