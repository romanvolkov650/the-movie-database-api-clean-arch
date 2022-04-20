package com.romanvolkov.themovie.data.di

import com.romanvolkov.themovie.core.AppImpl
import dagger.Component

@Component(
    modules = [
        DataModule::class
    ]
)
interface AppComponent {
    fun inject(app: AppImpl)
}