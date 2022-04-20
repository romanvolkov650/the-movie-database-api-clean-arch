package com.romanvolkov.themovie.core

import java.io.IOException

open class NoInternetException(override val message: String? = null) : IOException()