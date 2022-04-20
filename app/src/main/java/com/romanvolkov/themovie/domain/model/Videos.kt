package com.romanvolkov.themovie.domain.model

data class Videos(
    val id: Int,
    val results: List<VideosResult>
)

data class VideosResult(
    val key: String,
    val site: String,
    val official: Boolean
)