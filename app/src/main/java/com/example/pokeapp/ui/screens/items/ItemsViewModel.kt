package com.example.pokeapp.ui.screens.items

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokeapp.data.remote.NetworkModule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ItemsViewModel @Inject constructor(

) : ViewModel() {

    private val apiService = NetworkModule.api

    private val _state = MutableStateFlow(ItemUiState())
    val state: StateFlow<ItemUiState> = _state.asStateFlow()

    init {
        getItems()
    }

    fun getItems(limit: Int = 40, offset: Int = 0) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )

            try {
                val response = apiService.getItemList(limit, offset)

                val itemList = response.results.map { itemDto ->
                    ItemUiModel(
                        name = itemDto.name,
                        url = itemDto.url
                    )
                }

                _state.value = _state.value.copy(
                    isLoading = false,
                    items = itemList
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = e.message ?: "Bir hata oluştu"
                )
            }
        }
    }
}