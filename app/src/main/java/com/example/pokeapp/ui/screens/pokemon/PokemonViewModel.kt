package com.example.pokeapp.ui.screens.pokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.remote.NetworkModule
import com.example.pokeapp.data.repository.PokemonRepositoryImpl
import com.example.pokeapp.domain.repository.PokemonRepository
import com.example.pokeapp.ui.model.PokemonCardUiModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PokemonViewModel(
    private val repo: PokemonRepository = PokemonRepositoryImpl()
) : ViewModel() {

    private val apiService = NetworkModule.api

    private val _state = MutableStateFlow(pokemonUiState(isLoading = true))
    val state: StateFlow<pokemonUiState> = _state

    init {
        loadPokemon()
    }

    fun loadPokemon(limit: Int = 25, offset: Int = 0) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            val result = repo.getPokemonList(limit, offset)
            result
                .onSuccess { list ->
                    runCatching {
                        val detailedItems = list.map { pokemon ->
                            val detail = apiService.getPokemonDetail(pokemon.id)

                            PokemonCardUiModel(
                                id = pokemon.id,
                                name = pokemon.name,
                                url = pokemon.url,
                                imageUrl = pokemon.imageUrl,
                                types = detail.types
                                    .sortedBy { it.slot }
                                    .map { it.type.name }
                            )
                        }

                        _state.value = pokemonUiState(
                            isLoading = false,
                            items = detailedItems
                        )
                    }.onFailure { e ->
                        _state.value = pokemonUiState(
                            isLoading = false,
                            error = e.message ?: "Unknown error"
                        )
                    }
                }
                .onFailure { e ->
                    _state.value = pokemonUiState(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
        }
    }
}