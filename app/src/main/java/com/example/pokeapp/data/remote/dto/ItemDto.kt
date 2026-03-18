package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemDto(
    val name: String,
    val url: String
)