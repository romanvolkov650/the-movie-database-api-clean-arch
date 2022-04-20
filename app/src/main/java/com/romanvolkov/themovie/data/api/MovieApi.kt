package com.romanvolkov.themovie.data.api

import com.romanvolkov.themovie.data.model.MovieModel
import com.romanvolkov.themovie.data.model.PopularResponseModel
import com.romanvolkov.themovie.data.model.VideosModel
import com.romanvolkov.themovie.data.model.VideosResultModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

    @GET("3/movie/popular")
    suspend fun getPopular(
        @Query("page") page: Int = 1
    ): PopularResponseModel

    @GET("3/movie/{movie_id}")
    suspend fun getMovieDetail(
        @Path("movie_id") movieId: Int
    ): MovieModel

    @GET("3/movie/{movie_id}/videos")
    suspend fun getMovieTrailers(
        @Path("movie_id") movieId: Int
    ): VideosModel
}