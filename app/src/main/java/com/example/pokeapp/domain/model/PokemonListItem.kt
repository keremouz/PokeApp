package com.example.pokeapp.domain.model

data class PokemonListItem(
    val id: Int = 0,
    val name: String = "",
    val url: String = "",

) {
    val imageUrl: String
        get() = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$id.png"
}