package com.example.pokeapp.data.remote

import com.example.pokeapp.data.remote.dto.EvolutionChainResponseDto
import com.example.pokeapp.data.remote.dto.ItemDetailDto
import com.example.pokeapp.data.remote.dto.ItemListResponseDto
import com.example.pokeapp.data.remote.dto.PokemonListResponseDto
import retrofit2.http.Path
import com.example.pokeapp.data.remote.dto.PokemonDetailDto
import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApiService {


    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ):  PokemonListResponseDto

    @GET("item")
    suspend fun getItemList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): ItemListResponseDto

    @GET("item/{name}")
    suspend fun getItemDetail(
        @Path("name") name: String
    ): ItemDetailDto

    @GET("evolution-chain/{id}")
    suspend fun getEvolutionChain(
        @Path("id") id: Int
    ): EvolutionChainResponseDto

    @GET("pokemon/{id}")
    suspend fun getPokemonDetail(
        @Path("id") id: Int
    ): PokemonDetailDto

}