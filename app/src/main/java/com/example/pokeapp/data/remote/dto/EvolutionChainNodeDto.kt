package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChainNodeDto(
    val species: EvolutionSpeciesDto,
    @SerialName("evolves_to")
    val evolvesTo: List<EvolutionChainNodeDto> = emptyList()
)