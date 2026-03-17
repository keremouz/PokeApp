package com.example.pokeapp.ui.screens.evolution

data class EvolutionUiState(
    val isLoading: Boolean = false,
    val evolutionChains: List<EvolutionChainUiModel> = emptyList(),
    val error: String? = null
)