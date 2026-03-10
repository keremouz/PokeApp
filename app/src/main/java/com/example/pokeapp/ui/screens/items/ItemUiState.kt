package com.example.pokeapp.ui.screens.items

data class ItemUiState (
    val isLoading: Boolean= false,
    val items: List<ItemUiModel> = emptyList(),
    val error: String? = null
)