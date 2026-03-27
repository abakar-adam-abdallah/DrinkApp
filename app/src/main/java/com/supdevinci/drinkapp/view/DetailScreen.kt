package com.supdevinci.drinkapp.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.supdevinci.drinkapp.data.local.entities.CocktailEntity
import com.supdevinci.drinkapp.viewmodel.CocktailViewModel

@Composable
fun DetailScreen(
    cocktailId: Int,
    viewModel: CocktailViewModel = viewModel(),
    onBack: () -> Unit
) {
    var cocktail by remember { mutableStateOf<CocktailEntity?>(null) }

    LaunchedEffect(cocktailId) {
        cocktail = viewModel.getCocktailById(cocktailId)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Retour")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (cocktail != null) {
            if (!cocktail?.imageUrl.isNullOrBlank()) {
                AsyncImage(
                    model = cocktail?.imageUrl,
                    contentDescription = cocktail?.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(16.dp))
            }

            Text(
                text = cocktail!!.name,
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = cocktail!!.instructions,
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = if (cocktail!!.isFavorite) "⭐ Favori" else "Pas favori"
            )
        } else {
            Text("Cocktail introuvable.")
        }
    }
}