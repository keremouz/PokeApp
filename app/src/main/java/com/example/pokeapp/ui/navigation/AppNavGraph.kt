package com.example.pokeapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pokeapp.ui.screens.LoginScreen
import com.example.pokeapp.ui.screens.MainScreen
import com.example.pokeapp.ui.screens.RegisterEmailScreen
import com.example.pokeapp.ui.screens.auth.AuthViewModel
import com.example.pokeapp.ui.screens.items.ItemsScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    val startDestination =
        if (FirebaseAuth.getInstance().currentUser != null) Routes.MAIN
        else Routes.LOGIN

    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onGoRegister = { navController.navigate(Routes.REGISTER_EMAIL) }
            )
        }

        composable(Routes.REGISTER_EMAIL) {
            RegisterEmailScreen(
                onBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate(Routes.MAIN) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.MAIN) {
            val authVm: AuthViewModel = hiltViewModel()

            MainScreen(
                onLogout = {
                    authVm.logout()
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.MAIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Routes.ITEMS) {
            ItemsScreen()
        }
    }
}