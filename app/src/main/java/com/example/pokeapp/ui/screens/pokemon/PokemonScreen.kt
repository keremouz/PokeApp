package com.example.pokeapp.ui.screens.pokemon


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokeapp.ui.screens.favorites.FavoritesViewModel

@Composable
fun PokemonScreen(
    vm: PokemonViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    favoritesVm: FavoritesViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text("Hata: ${state.error}", color = MaterialTheme.colorScheme.error)
                Spacer(Modifier.height(12.dp))
                Button(onClick = { vm.loadPokemon() }) {
                    Text("Tekrar Dene")
                }
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                item {
                    Text(
                        text = "Pokemon count: ${state.items.size}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(8.dp))
                }

                items(state.items) { pokemon ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = pokemon.imageUrl,
                                    contentDescription = pokemon.name,
                                    modifier = Modifier.size(72.dp)
                                )

                                Spacer(modifier = Modifier.width(12.dp))

                                Column {
                                    Text(
                                        text = pokemon.name.replaceFirstChar { it.uppercase() },
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = "id: ${pokemon.id}",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                            }

                            TextButton(
                                onClick = {
                                    favoritesVm.toggle(
                                        pokemonId = pokemon.id,
                                        name = pokemon.name,
                                        url = pokemon.url
                                    )
                                }
                            ) {
                                Text("⭐")
                            }
                        }
                    }
                }
            }
        }
    }
}