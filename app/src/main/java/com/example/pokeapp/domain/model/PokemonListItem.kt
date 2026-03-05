package com.example.pokeapp.domain.model

data class PokemonListItem(
    val id: Int,
    val name: String,
    val url: String,


){
    val imageUrl: String
        get() = "package com.example.pokeapp.domain.model\n" +
                "\n" +
                "data class PokemonListItem(\n" +
                "    val name: String,\n" +
                "    val url: String\n" +
                "\n" +
                ")"
}