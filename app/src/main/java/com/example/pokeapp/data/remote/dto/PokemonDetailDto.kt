package com.example.pokeapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable

data class PokemonDetailDto(
    val id: Int,
    val name:  String,
    val sprites: PokemonSpritesDto,
    val types: List<PokemonTypeSlotDto>
)