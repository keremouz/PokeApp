package com.example.pokeapp.ui.screens.favorites

import com.example.pokeapp.domain.model.PokemonListItem

data class FavoritesUiState (
    val items: List<PokemonListItem> = emptyList(),
    val isLoading: Boolean = true,
    val error: String? = null
)