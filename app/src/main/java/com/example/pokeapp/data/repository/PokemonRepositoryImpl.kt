package com.example.pokeapp.data.repository

import com.example.pokeapp.data.remote.NetworkModule
import com.example.pokeapp.domain.model.PokemonListItem
import com.example.pokeapp.domain.repository.PokemonRepository

class PokemonRepositoryImpl(
    private val api: com.example.pokeapp.data.remote.PokeApiService = NetworkModule.api
) : PokemonRepository {

    override suspend fun getPokemonList(limit: Int, offset: Int): Result<List<PokemonListItem>> =
        runCatching {
            val response = api.getPokemonList(limit = limit, offset = offset)

            response.results.map { dto ->

                val id = dto.url
                    .trimEnd('/')
                    .substringAfterLast('/')
                    .toInt()

                PokemonListItem(
                    id = id,
                    name = dto.name,
                    url = dto.url
                )
            }
        }
}