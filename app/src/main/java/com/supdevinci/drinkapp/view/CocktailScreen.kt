package com.supdevinci.drinkapp.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.supdevinci.drinkapp.data.local.entities.CocktailEntity
import com.supdevinci.drinkapp.viewmodel.CocktailState
import com.supdevinci.drinkapp.viewmodel.CocktailViewModel

@Composable
fun CocktailScreen(
    viewModel: CocktailViewModel = viewModel(),
    onNavigateToDetail: (Int) -> Unit,
    onNavigateToFavorites: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var name by remember { mutableStateOf("") }
    var instructions by remember { mutableStateOf("") }
    var search by remember { mutableStateOf("") }

    val cocktails = when (state) {
        is CocktailState.Success -> (state as CocktailState.Success).cocktails
        else -> emptyList()
    }

    val filteredCocktails = cocktails.filter {
        it.name.contains(search, ignoreCase = true) ||
                it.instructions.contains(search, ignoreCase = true)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Drink App - Room",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onNavigateToFavorites,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Voir les favoris")
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = search,
            onValueChange = { search = it },
            label = { Text("Rechercher un cocktail") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nom du cocktail") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = instructions,
            onValueChange = { instructions = it },
            label = { Text("Instructions") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = {
                if (name.isNotBlank() && instructions.isNotBlank()) {
                    viewModel.addCocktail(name, instructions)
                    name = ""
                    instructions = ""
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ajouter")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { viewModel.fetchRandomCocktailFromApi() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ajouter un cocktail aléatoire depuis l'API")
        }

        Spacer(modifier = Modifier.height(20.dp))

        when {
            state is CocktailState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            state is CocktailState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = (state as CocktailState.Error).message,
                        color = Color.Red
                    )
                }
            }

            filteredCocktails.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = when {
                            cocktails.isEmpty() && search.isBlank() ->
                                "Aucun cocktail enregistré."
                            cocktails.isEmpty() && search.isNotBlank() ->
                                "Aucun cocktail à rechercher."
                            else ->
                                "Aucun résultat pour \"$search\"."
                        },
                        color = Color.Gray
                    )
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(filteredCocktails, key = { it.id }) { cocktail ->
                        CocktailItem(
                            cocktail = cocktail,
                            onToggleFavorite = { viewModel.toggleFavorite(cocktail) },
                            onDelete = { viewModel.deleteCocktail(cocktail) },
                            onClick = { onNavigateToDetail(cocktail.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CocktailItem(
    cocktail: CocktailEntity,
    onToggleFavorite: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.Top
        ) {
            AsyncImage(
                model = cocktail.imageUrl,
                contentDescription = cocktail.name,
                modifier = Modifier.size(80.dp),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = cocktail.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = cocktail.instructions,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row {
                    TextButton(onClick = onToggleFavorite) {
                        Text(
                            text = if (cocktail.isFavorite) "★ Favori" else "☆ Favori",
                            color = if (cocktail.isFavorite) Color(0xFFFFA000) else Color.Gray
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    TextButton(onClick = onDelete) {
                        Text("Supprimer", color = Color.Red)
                    }
                }

                HorizontalDivider(modifier = Modifier.padding(top = 6.dp))
            }
        }
    }
}