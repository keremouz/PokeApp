package com.example.pokeapp.ui.screens.evolution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.remote.NetworkModule
import com.example.pokeapp.data.remote.dto.EvolutionChainNodeDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EvolutionViewModel @Inject constructor() : ViewModel() {

    private val apiService = NetworkModule.api

    private val _state = MutableStateFlow(EvolutionUiState())
    val state: StateFlow<EvolutionUiState> = _state.asStateFlow()

    init {
        getEvolutionChains()
    }

    private fun getEvolutionChains() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val chains = mutableListOf<EvolutionChainUiModel>()

                for (id in 1..10) {
                    val response = apiService.getEvolutionChain(id)
                    val names = extractEvolutionNames(response.chain)

                    if (names.isNotEmpty()) {
                        val formattedNames = names.map { it.capitalizePokemonName() }

                        chains.add(
                            EvolutionChainUiModel(
                                title = formattedNames.first(),
                                stageCount = formattedNames.size,
                                pokemons = formattedNames.map { name ->
                                    EvolutionPokemonUiModel(
                                        name = name,
                                        imageUrl = getPokemonImageUrl(name.lowercase())
                                    )
                                }
                            )
                        )
                    }
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    evolutionChains = chains
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message
                )
            }
        }
    }

    private fun extractEvolutionNames(chain: EvolutionChainNodeDto): List<String> {
        val result = mutableListOf<String>()

        fun traverse(node: EvolutionChainNodeDto) {
            result.add(node.species.name)
            node.evolvesTo.forEach { child ->
                traverse(child)
            }
        }

        traverse(chain)
        return result.distinct()
    }

    private fun String.capitalizePokemonName(): String {
        return replaceFirstChar { char ->
            if (char.isLowerCase()) char.titlecase() else char.toString()
        }
    }
    private fun getPokemonImageUrl(name: String): String {
        return "https://img.pokemondb.net/sprites/home/normal/$name.png"
    }
}