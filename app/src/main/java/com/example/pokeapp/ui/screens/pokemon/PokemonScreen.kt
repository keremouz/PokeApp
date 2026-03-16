package com.example.pokeapp.ui.screens.pokemon

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokeapp.R
import com.example.pokeapp.ui.components.LoadingAnimation
import com.example.pokeapp.ui.components.PokemonCard
import com.example.pokeapp.ui.screens.favorites.FavoritesViewModel
import com.example.pokeapp.ui.theme.UiConstants
import androidx.compose.foundation.layout.padding

@Composable
fun PokemonScreen(
    vm: PokemonViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    favoritesVm: FavoritesViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()
    val favoritesState by favoritesVm.state.collectAsState()

    val favoriteIds = favoritesState.items.map { it.id }.toSet()

    when {
        state.isLoading -> {
            LoadingAnimation()
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
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    start = UiConstants.FavoriteListPadding,
                    top = UiConstants.FavoriteListPadding,
                    end = UiConstants.FavoriteListPadding,
                    bottom = UiConstants.BottomBarHeight + UiConstants.LargeSpacing
                ),
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
                    PokemonCard(
                        pokemon = pokemon,
                        isFavorite = pokemon.id in favoriteIds,
                        onFavoriteClick = {
                            favoritesVm.toggle(
                                pokemonId = pokemon.id,
                                name = pokemon.name,
                                url = pokemon.url
                            )
                        }
                    )
                }
            }
        }
    }
}