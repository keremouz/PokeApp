package com.example.pokeapp.domain.repository

import com.example.pokeapp.domain.model.PokemonListItem

interface PokemonRepository {
    suspend fun getPokemonList(limit: Int,offset: Int): Result<List<PokemonListItem>>
}