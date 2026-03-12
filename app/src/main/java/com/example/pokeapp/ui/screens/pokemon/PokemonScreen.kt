package com.example.pokeapp.ui.screens.pokemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import coil.compose.AsyncImage
import com.example.pokeapp.R
import com.example.pokeapp.ui.screens.favorites.FavoritesViewModel
import com.example.pokeapp.ui.theme.UiConstants
import androidx.compose.foundation.layout.height

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
                    .padding(UiConstants.ScreenOuterPadding),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = stringResource(
                        R.string.error_with_message,
                        state.error ?: ""
                    ),
                    color = MaterialTheme.colorScheme.error
                )

                Spacer(Modifier.height(UiConstants.MediumSpacing))

                Button(onClick = { vm.loadPokemon() }) {
                    Text(text = stringResource(R.string.retry))
                }
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(UiConstants.FavoriteListPadding),
                verticalArrangement = Arrangement.spacedBy(UiConstants.FavoriteItemSpacing)
            ) {
                item {
                    Text(
                        text = stringResource(R.string.pokemon_count, state.items.size),
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(Modifier.height(UiConstants.SmallSpacing))
                }

                items(state.items) { pokemon ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(UiConstants.FavoriteRowPadding),
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
                                    modifier = Modifier.size(UiConstants.PokemonImageSize)
                                )

                                Spacer(modifier = Modifier.width(UiConstants.ItemRowSpacing))

                                Column {
                                    Text(
                                        text = pokemon.name.replaceFirstChar { it.uppercase() },
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = stringResource(R.string.pokemon_id, pokemon.id),
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
                                Text(text = "⭐")
                            }
                        }
                    }
                }
            }
        }
    }
}