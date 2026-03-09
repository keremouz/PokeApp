package com.example.pokeapp.ui.screens.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.domain.repository.FavoritesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel

class FavoritesViewModel @Inject constructor(
    private val repo: FavoritesRepository
): ViewModel() {
    private val _state = MutableStateFlow(FavoritesUiState())
    val state: StateFlow<FavoritesUiState> = _state

    init {
        viewModelScope.launch {
            repo.observeFavorites().collect{ list->
                _state.update { it.copy(items = list, isLoading = false, error = null) }
            }
        }
    }
    fun toggle(pokemonId: Int,name: String, url: String){
        viewModelScope.launch {
            runCatching {
                repo.toggleFavorite(
                    com.example.pokeapp.domain.model.PokemonListItem(
                        id = pokemonId,
                        name = name,
                        url = url
                    )
                )
            }.onFailure { e->
                _state.update { it.copy(error = e.message) }
            }
        }
    }
}