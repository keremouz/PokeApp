package com.example.pokeapp.ui.screens.pokemon

import com.example.pokeapp.domain.model.PokemonListItem

data class pokemonUiState (
    val isLoading: Boolean = false,
    val items: List<PokemonListItem> = emptyList(),
    val error: String? = null


    )