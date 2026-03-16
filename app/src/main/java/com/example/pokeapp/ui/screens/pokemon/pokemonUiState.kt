package com.example.pokeapp.ui.screens.pokemon

import com.example.pokeapp.ui.model.PokemonCardUiModel

data class pokemonUiState(
    val isLoading: Boolean = false,
    val items: List<PokemonCardUiModel> = emptyList(),
    val error: String? = null
)