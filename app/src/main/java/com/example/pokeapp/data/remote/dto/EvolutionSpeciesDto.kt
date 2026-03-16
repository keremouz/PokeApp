package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EvolutionSpeciesDto(
    val name: String,
    val url: String
)