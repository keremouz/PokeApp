package com.example.pokeapp.ui.screens.evolution

data class EvolutionUiState(
    val isLoading: Boolean = false,
    val evolutionChains: List<String> = emptyList(),
    val error: String? = null
)