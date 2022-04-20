package com.romanvolkov.themovie.presentation.model

data class MovieState(
    val isLoading: Boolean = true,
    val imagePath: String? = null,
    val name: String = "",
    val rating: Int = 0,
    val trailerUrl: String = "",
    val tagline: String? = null,
    val description: String = ""
)