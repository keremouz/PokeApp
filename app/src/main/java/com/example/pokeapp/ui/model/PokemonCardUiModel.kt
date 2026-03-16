package com.example.pokeapp.ui.model

data class PokemonCardUiModel(
    val id: Int,
    val name: String,
    val url: String,
    val imageUrl: String,
    val types: List<String>
)