package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class ItemListResponseDto(
    val count: Int,
    val next: String?,
    val previous: String?,
    val results: List<ItemDto>
)
@Serializable
data class ItemDto(
    val name: String,
    val url: String
)