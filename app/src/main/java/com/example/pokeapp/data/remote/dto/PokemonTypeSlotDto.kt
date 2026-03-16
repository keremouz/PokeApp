package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeSlotDto (
    val slot: Int,
    val type: PokemonTypeDto
)