package com.example.pokeapp.ui.screens

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
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
import com.example.pokeapp.ui.theme.UiConstants

data class BottomNavItem(
    val route: String,
    @StringRes val labelResId: Int,
    @DrawableRes val iconResId: Int? = null,
    val imageVector: ImageVector? = null
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    onLogout: () -> Unit
) {
    val navController = rememberNavController()

    val tabs = listOf(
        BottomNavItem(
            route = Routes.POKEMON,
            labelResId = R.string.pokemon,
            iconResId = R.drawable.ic_pokeball
        ),
        BottomNavItem(
            route = Routes.ITEMS,
            labelResId = R.string.items,
            iconResId = R.drawable.ic_bag
        ),
        BottomNavItem(
            route = Routes.FAVORITES,
            labelResId = R.string.favorites,
            imageVector = Icons.Outlined.FavoriteBorder
        ),
        BottomNavItem(
            route = Routes.EVOLUTION,
            labelResId = R.string.evolution,
            imageVector = Icons.Outlined.AutoAwesome
        )
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
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(UiConstants.BottomBarHeight)
                    .background(UiConstants.BottomBarBackgroundColor),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                tabs.forEach { tab ->
                    val selected = currentRoute == tab.route

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .clickable {
                                navController.navigate(tab.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            .padding(vertical = UiConstants.BottomBarItemVerticalPadding),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        val tintColor = if (selected) {
                            if (tab.route == Routes.FAVORITES) {
                                UiConstants.BottomBarSelectedFavoriteColor
                            } else {
                                UiConstants.BottomBarSelectedTextColor
                            }
                        } else {
                            UiConstants.BottomBarUnselectedColor
                        }

                        when {
                            tab.route == Routes.FAVORITES && selected -> {
                                Icon(
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = stringResource(tab.labelResId),
                                    tint = tintColor,
                                    modifier = Modifier.size(UiConstants.BottomBarIconSize)
                                )
                            }

                            tab.iconResId != null -> {
                                Icon(
                                    painter = painterResource(id = tab.iconResId),
                                    contentDescription = stringResource(tab.labelResId),
                                    tint = Color.Unspecified,
                                    modifier = Modifier.size(UiConstants.BottomBarIconSize)
                                )
                            }

                            tab.imageVector != null -> {
                                Icon(
                                    imageVector = tab.imageVector,
                                    contentDescription = stringResource(tab.labelResId),
                                    tint = tintColor,
                                    modifier = Modifier.size(UiConstants.BottomBarIconSize)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(UiConstants.BottomBarIconLabelSpacing))

                        Text(
                            text = stringResource(tab.labelResId),
                            color = if (selected) {
                                UiConstants.BottomBarSelectedTextColor
                            } else {
                                UiConstants.BottomBarUnselectedColor
                            },
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
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