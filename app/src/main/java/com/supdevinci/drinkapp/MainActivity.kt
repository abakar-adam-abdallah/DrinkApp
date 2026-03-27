/*
package com.supdevinci.drinkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.supdevinci.drinkapp.ui.theme.DrinkAppTheme
import com.supdevinci.drinkapp.viewmodel.CocktailState
import com.supdevinci.drinkapp.viewmodel.CocktailViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: CocktailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DrinkAppTheme {
                CocktailScreen(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun CocktailScreen(viewModel: CocktailViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        when (state) {
            is CocktailState.Loading -> {
                CircularProgressIndicator()
            }

            is CocktailState.Success -> {
                val drink = (state as CocktailState.Success).drink

                Text(
                    text = drink.strDrink,
                    style = MaterialTheme.typography.headlineMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = drink.strInstructions,
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { viewModel.fetchCocktail() }
                ) {
                    Text("Charger un autre cocktail")
                }
            }

            is CocktailState.Error -> {
                val message = (state as CocktailState.Error).message

                Text(
                    text = message,
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}



*/

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