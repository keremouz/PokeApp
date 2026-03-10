package com.example.pokeapp.ui.screens.evolution

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.remote.NetworkModule
import com.example.pokeapp.data.remote.dto.ChainDto
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

    fun getEvolutionChains() {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val chains = mutableListOf<String>()

                for (id in 1..10) {
                    val response = apiService.getEvolutionChain(id)
                    val names = extractEvolutionNames(response.chain)
                    chains.add(names.joinToString(" -> ") { name ->
                        name.replaceFirstChar { it.uppercase() }
                    })
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    evolutionChains = chains
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Bir hata oluştu"
                )
            }
        }
    }

    private fun extractEvolutionNames(chain: ChainDto): List<String> {
        val result = mutableListOf<String>()

        fun traverse(node: ChainDto) {
            result.add(node.species.name)
            node.evolvesTo.forEach { child ->
                traverse(child)
            }
        }

        traverse(chain)
        return result
    }
}