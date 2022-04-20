package com.romanvolkov.themovie.presentation.viewmodel

import com.romanvolkov.themovie.core.Action

sealed class MovieActions : Action {
    class GetPopular(val page: Int) : MovieActions()
    object GetNextPopular : MovieActions()

    class GetMovieDetails(val movieId: Int) : MovieActions()
    class GetTrailer(val movieId: Int) : MovieActions()
}
