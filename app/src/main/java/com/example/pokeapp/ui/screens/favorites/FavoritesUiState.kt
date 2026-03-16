package com.example.pokeapp.ui.screens.favorites

import com.example.pokeapp.ui.model.PokemonCardUiModel

data class FavoritesUiState(
    val isLoading: Boolean = true,
    val items: List<PokemonCardUiModel> = emptyList(),
    val error: String? = null
)