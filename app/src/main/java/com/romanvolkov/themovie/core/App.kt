package com.romanvolkov.themovie.core

import com.romanvolkov.themovie.data.api.MovieApi
import com.romanvolkov.themovie.domain.repository.MovieRepository

interface App {
    fun getMovieRepository(): MovieRepository
}