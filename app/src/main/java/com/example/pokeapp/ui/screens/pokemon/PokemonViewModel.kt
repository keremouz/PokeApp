package com.example.pokeapp.ui.screens.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.repository.PokemonRepositoryImpl
import com.example.pokeapp.domain.repository.PokemonRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class PokemonViewModel (
    private val repo:  PokemonRepository = PokemonRepositoryImpl()

): ViewModel(){
    private val _state = MutableStateFlow(pokemonUiState(isLoading = true))
    val state: StateFlow<pokemonUiState> = _state

    init {
        loadPokemon()
    }
    fun loadPokemon(limit: Int = 20, offset: Int = 0){
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true ,error = null)

            val result = repo.getPokemonList(limit,offset)
            result
                .onSuccess { list ->
                    _state.value = pokemonUiState(isLoading = false,items = list)
                }
                .onFailure { e ->
                    _state.value = pokemonUiState(isLoading = false,error = e.message ?: "Unknown error")
                }
        }
    }
}
