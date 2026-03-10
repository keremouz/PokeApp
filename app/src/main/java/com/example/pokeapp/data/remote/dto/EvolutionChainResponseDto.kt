package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EvolutionChainResponseDto(
    val id: Int,
    val chain: ChainDto
)

@Serializable
data class ChainDto(
    val species: SpeciesDto,
    @SerialName("evolves_to")
    val evolvesTo: List<ChainDto> = emptyList()
)

@Serializable
data class SpeciesDto(
    val name: String,
    val url: String
)