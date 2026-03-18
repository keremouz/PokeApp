package com.example.pokeapp.ui.screens.items

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage
import com.example.pokeapp.ui.theme.UiConstants

@Composable
fun ItemCard (
    item: ItemUiModel,
    modifier : Modifier = Modifier
){
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(UiConstants.CardCornerRadius),
        elevation = CardDefaults.cardElevation(defaultElevation = UiConstants.CardElevation)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(UiConstants.CardPadding),
            horizontalArrangement = Arrangement.spacedBy(UiConstants.MediumSpacing),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = item.name,
                modifier = Modifier.size(UiConstants.ItemImageSize),
                contentScale = ContentScale.Fit
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(UiConstants.VerySmallSpacing)
            ){
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = item.category,
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(UiConstants.VerySmallSpacing))

                Text(
                    text = "cost:${item.cost}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}