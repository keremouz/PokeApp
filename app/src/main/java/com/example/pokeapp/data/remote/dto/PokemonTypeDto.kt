package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PokemonTypeDto (
    val name: String,
    val url: String
)