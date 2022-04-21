package com.romanvolkov.themovie.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.romanvolkov.themovie.core.Action
import com.romanvolkov.themovie.core.DispatchViewModel
import com.romanvolkov.themovie.core.common.Constants.SITE_YOUTUBE
import com.romanvolkov.themovie.core.common.Constants.STATUS_CONNECTION_ERROR
import com.romanvolkov.themovie.core.common.Constants.STATUS_OK
import com.romanvolkov.themovie.core.common.Constants.YOUTUBE_VIDEO_URL
import com.romanvolkov.themovie.core.common.LiveDataNotNull
import com.romanvolkov.themovie.domain.interactor.MovieInteractor
import com.romanvolkov.themovie.presentation.model.MovieState
import com.romanvolkov.themovie.presentation.model.PopularState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieInteractor: MovieInteractor
) : DispatchViewModel() {

    val state = LiveDataNotNull(PopularState())
    val movieDetailState = LiveDataNotNull(MovieState())
    private var page = 1

    override suspend fun reduce(action: Action) {
        super.reduce(action)
        when (action) {
            is MovieActions.GetPopular -> withContext(Dispatchers.IO) {
                getPopular(action.page)
            }

            is MovieActions.GetNextPopular -> withContext(Dispatchers.IO) {
                getPopular(page + 1)
            }

            is MovieActions.GetMovieDetails -> withContext(Dispatchers.IO) {
                getMovieDetails(action.movieId)
            }

            is MovieActions.GetTrailer -> withContext(Dispatchers.IO) {
                getTrailer(action.movieId)
            }
        }
    }

    // получить список популярных фильмов
    private suspend fun getPopular(page: Int) {
        viewModelScope.launch {
            state.value = state.value.copy(
                isPageLoading = page != 1
            )
        }
        movieInteractor.getPopular(
            page,
            onSuccess = {
                this.page = it.page
                viewModelScope.launch {
                    state.value = state.value.copy(
                        isLoading = false,
                        movies = (state.value.movies ?: emptyList()) + it.results,
                        isPageLoading = false,
                        hasNext = it.page != it.totalPages,
                        currentPage = it.page,
                        status = STATUS_OK
                    )
                }
            },
            onError = {
                viewModelScope.launch {
                    state.value = state.value.copy(
                        isLoading = false,
                        isPageLoading = false,
                        hasNext = false,
                        status = STATUS_CONNECTION_ERROR
                    )
                }
                dispatch { Action.Error(it) }
            }
        )
    }

    // получить информацию о фильме
    private suspend fun getMovieDetails(movieId: Int) {
        viewModelScope.launch {
            movieDetailState.value = movieDetailState.value.copy(
                isLoading = true
            )
        }
        movieInteractor.getMovieDetail(movieId, onSuccess = {
            viewModelScope.launch {
                movieDetailState.value = movieDetailState.value.copy(
                    imagePath = it.posterPath ?: "",
                    name = it.name,
                    rating = it.rating,
                    tagline = it.tagline,
                    description = it.description,
                    isLoading = false
                )
            }
        }, onError = {
            dispatch { Action.Error(it) }
        })
    }

    private suspend fun getTrailer(movieId: Int) {
        movieInteractor.getMovieTrailer(movieId, onSuccess = {
            viewModelScope.launch {
                val res =
                    it.results.sortedBy { it.official }.firstOrNull { it.site == SITE_YOUTUBE }
                res?.let {
                    movieDetailState.value = movieDetailState.value.copy(
                        trailerUrl = YOUTUBE_VIDEO_URL + res.key
                    )
                }
            }
        }, onError = {
            dispatch { Action.Error(it) }
        })
    }

}