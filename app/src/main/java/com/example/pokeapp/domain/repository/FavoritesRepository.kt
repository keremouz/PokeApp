package com.example.pokeapp.domain.repository

import com.example.pokeapp.domain.model.PokemonListItem
import kotlinx.coroutines.flow.Flow

interface  FavoritesRepository {
    fun observeFavorites(): Flow<List<PokemonListItem>>
    suspend fun toggleFavorite(Pokemon: PokemonListItem)
    suspend fun isFavorite(pokemonId: Int): Boolean

}