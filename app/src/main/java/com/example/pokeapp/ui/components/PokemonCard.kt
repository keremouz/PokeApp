package com.example.pokeapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.pokeapp.R
import com.example.pokeapp.ui.model.PokemonCardUiModel
import com.example.pokeapp.ui.theme.UiConstants

@Composable
fun PokemonCard(
    pokemon: PokemonCardUiModel,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit
) {
    val cardBackground = getPokemonCardBackground(pokemon.types.firstOrNull())
    val imageBackground = getPokemonImageBackground(pokemon.types.firstOrNull())

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(UiConstants.PokemonCardCornerRadius))
            .background(cardBackground)
            .padding(UiConstants.PokemonCardPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = stringResource(R.string.pokemon_number, pokemon.id),
                color = Color(0xFF6B6B6B)
            )

            Spacer(modifier = Modifier.height(UiConstants.PokemonCardSmallSpacing))

            Text(
                text = pokemon.name.replaceFirstChar { it.uppercase() },
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(UiConstants.PokemonCardMediumSpacing))

            Row(
                horizontalArrangement = Arrangement.spacedBy(UiConstants.PokemonCardSmallSpacing)
            ) {
                pokemon.types.take(2).forEach { type ->
                    TypeChip(type = type)
                }
            }
        }

        Spacer(modifier = Modifier.width(UiConstants.PokemonCardLargeSpacing))

        Box(
            modifier = Modifier
                .size(
                    width = UiConstants.PokemonCardImageBoxWidth,
                    height = UiConstants.PokemonCardImageBoxHeight
                )
                .clip(RoundedCornerShape(UiConstants.PokemonCardCornerRadius))
                .background(imageBackground),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(UiConstants.PokemonCardImageSize)
            )

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(UiConstants.PokemonCardFavoriteBadgePadding)
                    .size(UiConstants.PokemonCardFavoriteBadgeSize)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.65f))
                    .clickable { onFavoriteClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isFavorite) {
                        Icons.Filled.Favorite
                    } else {
                        Icons.Outlined.FavoriteBorder
                    },
                    contentDescription = stringResource(R.string.favorite),
                    tint = if (isFavorite) Color(0xFFFF5A7A) else Color.Gray,
                    modifier = Modifier.size(UiConstants.PokemonCardFavoriteIconSize)
                )
            }
        }
    }
}

@Composable
private fun TypeChip(type: String) {
    Surface(
        shape = RoundedCornerShape(50),
        color = getTypeColor(type)
    ) {
        Text(
            text = type.replaceFirstChar { it.uppercase() },
            color = Color.White,
            modifier = Modifier.padding(
                horizontal = UiConstants.TypeChipHorizontalPadding,
                vertical = UiConstants.TypeChipVerticalPadding
            )
        )
    }
}

private fun getTypeColor(type: String): Color {
    return when (type.lowercase()) {
        "fire" -> Color(0xFFF08A3E)
        "flying" -> Color(0xFF7B9FEF)
        "psychic" -> Color(0xFFF26D7D)
        "fighting" -> Color(0xFFC65A7C)
        "steel" -> Color(0xFF6E9FB2)
        "grass" -> Color(0xFF53B55A)
        "poison" -> Color(0xFFA35BCB)
        "water" -> Color(0xFF4A90E2)
        "electric" -> Color(0xFFF4C542)
        "normal" -> Color(0xFFA8A77A)
        else -> Color(0xFF9E9E9E)
    }
}

private fun getPokemonCardBackground(type: String?): Color {
    return when (type?.lowercase()) {
        "fire" -> Color(0xFFF8EFE7)
        "psychic" -> Color(0xFFF9ECEF)
        "grass" -> Color(0xFFEAF3E8)
        "fighting" -> Color(0xFFF7E7EF)
        "water" -> Color(0xFFEAF4FB)
        "electric" -> Color(0xFFFCF6DD)
        else -> Color(0xFFF5F5F5)
    }
}

private fun getPokemonImageBackground(type: String?): Color {
    return when (type?.lowercase()) {
        "fire" -> Color(0xFFF7A24F)
        "psychic" -> Color(0xFFF58E9B)
        "grass" -> Color(0xFF74C95B)
        "fighting" -> Color(0xFFD9608E)
        "water" -> Color(0xFF6FB6FF)
        "electric" -> Color(0xFFF2CD4D)
        else -> Color(0xFFE0E0E0)
    }
}