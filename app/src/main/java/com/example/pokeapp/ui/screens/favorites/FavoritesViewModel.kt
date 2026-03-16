package com.example.pokeapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.remote.NetworkModule
import com.example.pokeapp.domain.model.PokemonListItem
import com.example.pokeapp.domain.repository.FavoritesRepository
import com.example.pokeapp.ui.model.PokemonCardUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val repo: FavoritesRepository
) : ViewModel() {

    private val apiService = NetworkModule.api

    private val _state = MutableStateFlow(FavoritesUiState())
    val state: StateFlow<FavoritesUiState> = _state

    init {
        observeFavorites()
    }

    private fun observeFavorites() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            repo.observeFavorites().collect { list ->
                runCatching {
                    val detailedItems = list.map { favorite ->
                        val detail = apiService.getPokemonDetail(favorite.id)

                        PokemonCardUiModel(
                            id = favorite.id,
                            name = favorite.name,
                            url = favorite.url,
                            imageUrl = detail.sprites.frontDefault ?: "",
                            types = detail.types
                                .sortedBy { it.slot }
                                .map { it.type.name }
                        )
                    }

                    _state.update {
                        it.copy(
                            items = detailedItems,
                            isLoading = false,
                            error = null
                        )
                    }
                }.onFailure { e ->
                    _state.update {
                        it.copy(
                            isLoading = false,
                            error = e.message
                        )
                    }
                }
            }
        }
    }

    fun toggle(pokemonId: Int, name: String, url: String) {
        viewModelScope.launch {
            runCatching {
                repo.toggleFavorite(
                    PokemonListItem(
                        id = pokemonId,
                        name = name,
                        url = url
                    )
                )
            }.onFailure { e ->
                _state.update { it.copy(error = e.message) }
            }
        }
    }
}