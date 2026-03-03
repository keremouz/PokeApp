package com.example.pokeapp.ui.screens
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun FavoritesScreen() {


    val favorites: List<String>? =null

    if (favorites == null){
        Text("Henüz Favori Pokemon yok.")
    }
    else {
        Text("Favorites count: ${favorites.size}")
    }
}