package com.romanvolkov.themovie.domain.model

data class Movie(
    val adult: Boolean,
    val budget: Int,
    val id: Int,
    val name: String,
    val posterPath: String?,
    val rating: Int,
    val tagline: String?,
    val description: String,
)