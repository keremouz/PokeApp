package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class PokemonResultDto(
    @SerialName("name") val name: String,
    @SerialName("url") val url: String
)