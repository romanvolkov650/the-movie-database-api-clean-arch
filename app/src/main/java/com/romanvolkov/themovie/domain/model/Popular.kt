package com.romanvolkov.themovie.domain.model

data class PopularResponse(
    val page: Int,
    val results: List<Popular>,
    val totalResults: Int,
    val totalPages: Int
)

data class Popular(
    val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    val releaseDate: String?,
    val id: Int,
    val originalTitle: String,
    val originalLanguage: String,
    val title: String,
    val popularity: Float,
    val voteCount: Int,
    val video: Boolean,
    val voteAverage: Float
)