package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.Serializable


@Serializable
data class ItemCategoryDto (
    val name: String,
    val url: String
)