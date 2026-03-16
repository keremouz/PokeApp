package com.example.pokeapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.pokeapp.ui.language.LanguageManager
import com.example.pokeapp.ui.navigation.AppNavGraph
import com.example.pokeapp.ui.theme.PokeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        LanguageManager.applySavedLanguage(this)
        super.onCreate(savedInstanceState)

        setContent {
            PokeAppTheme {
                AppNavGraph()
            }
        }
    }
}