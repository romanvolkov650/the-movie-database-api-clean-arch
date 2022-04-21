package com.romanvolkov.themovie.core

import android.app.Application
import com.romanvolkov.themovie.data.di.AppComponent
import com.romanvolkov.themovie.data.di.DaggerAppComponent
import com.romanvolkov.themovie.domain.repository.MovieRepository
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class AppImpl : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}