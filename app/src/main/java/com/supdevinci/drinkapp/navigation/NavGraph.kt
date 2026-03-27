package com.supdevinci.drinkapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.supdevinci.drinkapp.view.*

@Composable
fun NavGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "splash"
    ) {

        // Splash
        composable("splash") {
            SplashScreen(
                onNavigateToHome = {
                    navController.navigate("list") {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            )
        }

        // Liste (CocktailScreen)
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

        // Detail
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

        // Favoris
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