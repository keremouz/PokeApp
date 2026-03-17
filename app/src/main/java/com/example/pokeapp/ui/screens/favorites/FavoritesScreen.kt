package com.example.pokeapp.ui.screens.favorites

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.pokeapp.R
import com.example.pokeapp.ui.components.LoadingAnimation
import com.example.pokeapp.ui.components.PokemonCard
import com.example.pokeapp.ui.theme.UiConstants
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.text.style.TextAlign

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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(UiConstants.MediumSpacing)
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_empty_favorites),
                        contentDescription = stringResource(R.string.empty_favorites_image_desc),
                        modifier = Modifier.size(UiConstants.EmptyFavoritesImageSize)
                    )

                    Text(
                        text = stringResource(R.string.empty_favorites_title),
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = stringResource(R.string.empty_favorites_description),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
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
                    bottom = UiConstants.FavoriteListPadding
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