package com.example.pokeapp.ui.screens.auth


data class AuthUiState (
    val email: String="",
    val password: String= "",
    val isLoading: Boolean = false,
    val error:  String? = null
)
