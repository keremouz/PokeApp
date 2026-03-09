package com.example.pokeapp.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import com.example.pokeapp.ui.navigation.Routes
import com.example.pokeapp.ui.screens.favorites.FavoritesScreen
import com.example.pokeapp.ui.screens.pokemon.PokemonScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    val tabs = listOf(
        Routes.POKEMON to "Pokemon",
        Routes.ITEMS to "Items",
        Routes.EVOLUTION to "Evolution",
        Routes.FAVORITES to "Favorites"
    )

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("PokeApp") },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text("Çıkış")
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                val currentRoute =
                    navController.currentBackStackEntryAsState().value?.destination?.route

                tabs.forEach { (route, label) ->
                    NavigationBarItem(
                        selected = currentRoute == route,
                        onClick = {
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {}, // sonra ikon ekleriz
                        label = { Text(label) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = Routes.POKEMON,
            modifier = Modifier.padding(padding)
        ) {
            composable(Routes.POKEMON) { PokemonScreen() }
            composable(Routes.ITEMS) { ItemsScreen() }
            composable(Routes.EVOLUTION) { EvolutionScreen() }
            composable(Routes.FAVORITES) { FavoritesScreen() }
        }
    }
}