package com.example.pokeapp.data.remote.dto

import com.example.pokeapp.domain.model.PokemonListItem

private fun String.extractPokemonId(): Int {
    return trimEnd('/')
        .split('/')
        .last()
        .toInt()
}
fun PokemonResultDto.toDomain(): PokemonListItem{
    val id = url.extractPokemonId()
    return PokemonListItem(
        id = id,
        name = name,
        url = url,

    )
}