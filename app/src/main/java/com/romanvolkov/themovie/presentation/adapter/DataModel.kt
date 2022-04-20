package com.romanvolkov.themovie.presentation.adapter

sealed class DataModel(open val id: Int) {
    data class Movie(
        override val id: Int,
        val posterPath: String?,
        val title: String,
        val date: String,
        val description: String,
        val itemClick: (Int) -> Unit
    ) : DataModel(id)

    data class Pagination(
        override val id: Int,
    ) : DataModel(id)
}