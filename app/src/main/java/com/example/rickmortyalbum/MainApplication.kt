package com.example.rickmortyalbum

import android.app.Application
import com.example.rickmortyalbum.di.charactersModule
import com.example.rickmortyalbum.di.episodesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@MainApplication)
            modules(listOf(com.example.rickmortyalbum.di.module, charactersModule, episodesModule))
        }
    }
}