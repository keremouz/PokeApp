package com.example.pokeapp

import android.app.Application
import com.example.pokeapp.ui.language.LanguageManager
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PokeAppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        LanguageManager.applySavedLanguage(this)
    }
}