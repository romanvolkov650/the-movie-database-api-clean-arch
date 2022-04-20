package com.romanvolkov.themovie.data.model

import com.google.gson.annotations.SerializedName
import com.romanvolkov.themovie.domain.model.Popular

data class PopularResponseModel(
    val page: Int,
    val results: List<PopularModel>,
    @SerializedName("total_results")
    val totalResults: Int,
    @SerializedName("total_pages")
    val totalPages: Int
)

data class PopularModel(
    @SerializedName("poster_path")
    val posterPath: String?,
    val adult: Boolean,
    val overview: String,
    @SerializedName("release_date")
    val releaseDate: String?,
    val id: Int,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    val title: String,
    val popularity: Float,
    val voteCount: Int,
    val video: Boolean,
    val voteAverage: Float
)