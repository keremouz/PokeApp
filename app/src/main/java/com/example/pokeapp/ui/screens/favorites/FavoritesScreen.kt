package com.example.pokeapp.ui.screens.favorites

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokeapp.R
import com.example.pokeapp.ui.components.LoadingAnimation
import com.example.pokeapp.ui.components.PokemonCard
import com.example.pokeapp.ui.theme.UiConstants

@Composable
fun FavoritesScreen(
    vm: FavoritesViewModel = hiltViewModel()
) {
    val state by vm.state.collectAsState()

    when {
        state.isLoading -> {
            LoadingAnimation()
        }

        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(
                        R.string.error_with_message,
                        state.error ?: ""
                    ),
                    color = MaterialTheme.colorScheme.error
                )
            }
        }

        state.items.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.no_favorites))
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
                items(state.items) { pokemon ->
                    PokemonCard(
                        pokemon = pokemon,
                        isFavorite = true,
                        onFavoriteClick = {
                            vm.toggle(
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