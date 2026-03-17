package com.example.pokeapp.ui.screens.evolution

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.pokeapp.R
import com.example.pokeapp.ui.theme.UiConstants

@Composable
fun EvolutionCard(
    chain: EvolutionChainUiModel,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(UiConstants.CardCornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = UiConstants.CardElevation)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(UiConstants.EvolutionCardHeight)
                .clip(RoundedCornerShape(UiConstants.CardCornerRadius))
                .background(backgroundColor)
                .padding(UiConstants.CardPadding)
        ) {
            Column(
                modifier = Modifier.align(Alignment.CenterStart),
                verticalArrangement = Arrangement.spacedBy(UiConstants.SmallSpacing)
            ) {
                Text(
                    text = chain.title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = pluralStringResource(
                        id = R.plurals.evolution_stage_count,
                        count = chain.stageCount,
                        chain.stageCount
                    ),
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Row(
                modifier = Modifier.align(Alignment.CenterEnd),
                horizontalArrangement = Arrangement.spacedBy(UiConstants.SmallSpacing),
                verticalAlignment = Alignment.CenterVertically
            ) {
                chain.pokemons.forEachIndexed { index, pokemon ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(UiConstants.VerySmallSpacing)
                    ) {
                        AsyncImage(
                            model = pokemon.imageUrl,
                            contentDescription = pokemon.name,
                            modifier = Modifier.size(UiConstants.EvolutionImageSize)
                        )

                        Text(
                            text = pokemon.name,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    if (index != chain.pokemons.lastIndex) {
                        Text(
                            text = "→",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}