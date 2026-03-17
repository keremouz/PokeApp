package com.example.pokeapp.ui.screens.evolution

data class EvolutionChainUiModel (
    val title: String,
    val  stageCount: Int,
    val pokemons: List<EvolutionPokemonUiModel>
)
