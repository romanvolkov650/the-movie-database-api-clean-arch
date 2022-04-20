package com.romanvolkov.themovie.core

interface Action {
    class Error(val error: Throwable, val method: String? = null, val tag: String? = null) : Action
}