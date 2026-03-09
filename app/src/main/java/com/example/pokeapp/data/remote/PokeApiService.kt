package com.example.pokeapp.data.remote

import com.example.pokeapp.data.remote.dto.PokemonListResponseDto

import retrofit2.http.GET
import retrofit2.http.Query

interface PokeApiService {


    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ):  PokemonListResponseDto
    /*
    @GET("item")
    suspend fun getItemList(
        @Query("limit")limit: Int,
        @Query("offset")offset: Int
    ):ItemListIntents

     */
}