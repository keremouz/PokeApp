package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ItemDetailDto (
    val name: String,
    val cost: Int,
    val category: ItemCategoryDto,
    val sprites: ItemSpritesDto
)