package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ItemSpritesDto (
    @SerialName("default")
    val defaultImage: String? = null
)