package com.romanvolkov.themovie.presentation.model

import com.romanvolkov.themovie.core.common.Constants.STATUS_OK
import com.romanvolkov.themovie.domain.model.Popular

data class PopularState(
    val isLoading: Boolean = true,
    val movies: List<Popular>? = emptyList(),
    val currentPage: Int = 1,
    val hasNext: Boolean = false,
    val isPageLoading: Boolean = false,
    val status: String = STATUS_OK
)
