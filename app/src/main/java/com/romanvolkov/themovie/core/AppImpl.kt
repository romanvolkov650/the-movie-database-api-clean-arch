package com.romanvolkov.themovie.core

import android.app.Application
import com.romanvolkov.themovie.data.di.AppComponent
import com.romanvolkov.themovie.data.di.DaggerAppComponent
import com.romanvolkov.themovie.domain.repository.MovieRepository
import timber.log.Timber
import javax.inject.Inject

class AppImpl : Application(), App {

    private val appComponent: AppComponent by lazy { DaggerAppComponent.builder().build() }

    @Inject
    lateinit var movieRepositoryImpl: MovieRepository

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        appComponent.inject(this)
    }

    override fun getMovieRepository(): MovieRepository = movieRepositoryImpl
}