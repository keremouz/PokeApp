package com.example.pokeapp.ui.screens.pokemon

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun PokemonScreen(
    vm: PokemonViewModel = viewModel()
) {
    val state by vm.state.collectAsState()

    Column {
        Text("Pokemon count: ${state.items.size}")

        state.items.firstOrNull()?.let { first ->
            Text("First: ${first.name}")
        }

        state.error?.let { err ->
            Text("Error: $err")
        }
    }
}