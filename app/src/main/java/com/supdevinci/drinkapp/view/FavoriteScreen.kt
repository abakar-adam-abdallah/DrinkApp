package com.supdevinci.drinkapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.supdevinci.drinkapp.viewmodel.CocktailState
import com.supdevinci.drinkapp.viewmodel.CocktailViewModel

@Composable
fun FavoriteScreen(
    viewModel: CocktailViewModel = viewModel(),
    onNavigateToDetail: (Int) -> Unit,
    onBack: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.observeFavorites()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retour")
        }

        Text(
            text = "Mes favoris",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        when (state) {
            is CocktailState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Chargement...")
                }
            }

            is CocktailState.Empty -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Aucun favori.")
                }
            }

            is CocktailState.Success -> {
                val cocktails = (state as CocktailState.Success).cocktails

                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(cocktails, key = { it.id }) { cocktail ->
                        CocktailItem(
                            cocktail = cocktail,
                            onToggleFavorite = { viewModel.toggleFavorite(cocktail) },
                            onDelete = { viewModel.deleteCocktail(cocktail) },
                            onClick = { onNavigateToDetail(cocktail.id) }
                        )
                    }
                }
            }

            is CocktailState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text((state as CocktailState.Error).message)
                }
            }
        }
    }
}