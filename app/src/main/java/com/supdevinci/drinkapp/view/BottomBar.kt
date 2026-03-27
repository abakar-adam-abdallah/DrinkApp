package com.supdevinci.drinkapp.view

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalBar
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun BottomBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("list") },
            icon = { Icon(Icons.Default.Home, contentDescription = "Accueil") },
            label = { Text("Accueil") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("favorites") },
            icon = { Icon(Icons.Default.Star, contentDescription = "Favoris") },
            label = { Text("Favoris") }
        )

        NavigationBarItem(
            selected = false,
            onClick = { navController.navigate("list") },
            icon = { Icon(Icons.Default.LocalBar, contentDescription = "Bar") },
            label = { Text("Bar") }
        )
    }
}