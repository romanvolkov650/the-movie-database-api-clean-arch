package com.romanvolkov.themovie.data.extensions

import com.romanvolkov.themovie.data.model.*
import com.romanvolkov.themovie.domain.model.*

fun PopularResponseModel.toPopularResponse(): PopularResponse = PopularResponse(
    page, results.map { it.toPopular() }, totalResults, totalPages
)

fun PopularModel.toPopular(): Popular = Popular(
    posterPath,
    adult,
    overview,
    releaseDate,
    id,
    originalTitle,
    originalLanguage,
    title,
    popularity,
    voteCount,
    video,
    voteAverage
)

fun MovieModel.toMovie(): Movie = Movie(
    adult, budget, id, title, posterPath, (rating * 10).toInt(), tagline, overview
)

fun VideosModel.toVideos(): Videos = Videos(
    id, results.map { it.toVideoResult() }
)

fun VideosResultModel.toVideoResult(): VideosResult = VideosResult(
    key, site, official
)