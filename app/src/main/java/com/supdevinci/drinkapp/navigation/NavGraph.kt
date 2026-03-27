package com.supdevinci.drinkapp.navigation

import androidx.compose.foundation.layout.padding

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.supdevinci.drinkapp.view.BottomBar
import com.supdevinci.drinkapp.view.CocktailScreen
import com.supdevinci.drinkapp.view.DetailScreen
import com.supdevinci.drinkapp.view.FavoriteScreen
import com.supdevinci.drinkapp.view.SplashScreen

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            BottomBar(navController)
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "splash",
            modifier = Modifier.padding(padding)
        ) {

            composable("splash") {
                SplashScreen(
                    onNavigateToHome = {
                        navController.navigate("list") {
                            popUpTo("splash") { inclusive = true }
                        }
                    }
                )
            }

            composable("list") {
                CocktailScreen(
                    onNavigateToDetail = { id ->
                        navController.navigate("detail/$id")
                    },
                    onNavigateToFavorites = {
                        navController.navigate("favorites")
                    }
                )
            }

            composable(
                route = "detail/{id}",
                arguments = listOf(
                    navArgument("id") { type = NavType.IntType }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id") ?: 0

                DetailScreen(
                    cocktailId = id,
                    onBack = { navController.popBackStack() }
                )
            }

            composable("favorites") {
                FavoriteScreen(
                    onNavigateToDetail = { id ->
                        navController.navigate("detail/$id")
                    },
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}