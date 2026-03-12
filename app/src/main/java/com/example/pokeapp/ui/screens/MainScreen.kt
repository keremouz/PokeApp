package com.example.pokeapp.ui.screens

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.R
import com.example.pokeapp.ui.navigation.Routes
import com.example.pokeapp.ui.screens.evolution.EvolutionScreen
import com.example.pokeapp.ui.screens.favorites.FavoritesScreen
import com.example.pokeapp.ui.screens.items.ItemsScreen
import com.example.pokeapp.ui.screens.pokemon.PokemonScreen

data class BottomNavItem(
    val route: String,
    @StringRes val labelResId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    val tabs = listOf(
        BottomNavItem(Routes.POKEMON, R.string.pokemon),
        BottomNavItem(Routes.ITEMS, R.string.items),
        BottomNavItem(Routes.EVOLUTION, R.string.evolution),
        BottomNavItem(Routes.FAVORITES, R.string.favorites)
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(stringResource(R.string.app_name)) },
                actions = {
                    TextButton(onClick = onLogout) {
                        Text(stringResource(R.string.logout))
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                tabs.forEach { tab ->
                    NavigationBarItem(
                        selected = currentRoute == tab.route,
                        onClick = {
                            navController.navigate(tab.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {},
                        label = { Text(stringResource(tab.labelResId)) }
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