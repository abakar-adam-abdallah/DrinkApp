package com.supdevinci.drinkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.supdevinci.drinkapp.navigation.NavGraph
import com.supdevinci.drinkapp.ui.theme.DrinkAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrinkAppTheme {
                NavGraph()
            }
        }
    }
}