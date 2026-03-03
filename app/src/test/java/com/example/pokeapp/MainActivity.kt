package com.example.pokeapp.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier


@Composable
fun MainScreen() {

    var selected by remember { mutableStateOf(0) }

    val items = listOf("Pokemon", "Items", "Evolution", "Favorites")

    Scaffold(
        bottomBar = {
            NavigationBar {
                items.forEachIndexed { index, label ->
                    NavigationBarItem(
                        selected = selected == index,
                        onClick = { selected = index },
                        icon = { },
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            Text("Selected: ${items[selected]}")
        }
    }
}