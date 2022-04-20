package com.romanvolkov.themovie.data.model

data class VideosModel(
    val id: Int,
    val results: List<VideosResultModel>
)

data class VideosResultModel(
    val key: String,
    val site: String,
    val official: Boolean
)