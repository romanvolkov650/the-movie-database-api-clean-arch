package com.romanvolkov.themovie.data.model

import com.google.gson.annotations.SerializedName

data class MovieModel(
    val adult: Boolean,
    val budget: Int,
    val id: Int,
    val title: String,
    @SerializedName("backdrop_path")
    val posterPath: String?,
    @SerializedName("vote_average")
    val rating: Float,
    val tagline: String?,
    val overview: String,
)