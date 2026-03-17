package com.example.pokeapp.ui.screens.evolution

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.pokeapp.R
import com.example.pokeapp.ui.theme.UiConstants

@Composable
fun EvolutionScreen(
    viewModel: EvolutionViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = UiConstants.ScreenPadding)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.error != null -> {
                Text(
                    text = state.error ?: stringResource(R.string.generic_error),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(vertical = UiConstants.ScreenPadding),
                    verticalArrangement = Arrangement.spacedBy(UiConstants.ItemSpacing)
                ) {
                    item {
                        Text(
                            text = stringResource(R.string.evolution),
                            modifier = Modifier.padding(bottom = UiConstants.SmallSpacing)
                        )
                    }

                    itemsIndexed(state.evolutionChains) { index, chain ->
                        EvolutionCard(
                            chain = chain,
                            backgroundColor = when (index % 6) {
                                0 -> UiConstants.EvolutionCardColor1
                                1 -> UiConstants.EvolutionCardColor2
                                2 -> UiConstants.EvolutionCardColor3
                                3 -> UiConstants.EvolutionCardColor4
                                4 -> UiConstants.EvolutionCardColor5
                                else -> UiConstants.EvolutionCardColor6
                            }
                        )
                    }
                }
            }
        }
    }
}