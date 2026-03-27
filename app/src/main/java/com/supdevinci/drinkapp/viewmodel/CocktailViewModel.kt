package com.supdevinci.drinkapp.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.supdevinci.drinkapp.data.local.CocktailDatabase
import com.supdevinci.drinkapp.data.local.entities.CocktailEntity
import com.supdevinci.drinkapp.data.local.remote.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class CocktailViewModel(application: Application) : AndroidViewModel(application) {

    private val dao = CocktailDatabase.getDatabase(application).cocktailDao()
    private val api = RetrofitInstance.api

    private val _state = MutableStateFlow<CocktailState>(CocktailState.Loading)
    val state: StateFlow<CocktailState> = _state.asStateFlow()

    init {
        observeCocktails()
    }

    private fun observeCocktails() {
        viewModelScope.launch {
            try {
                dao.getAllCocktails().collect { cocktails ->
                    _state.value = if (cocktails.isEmpty()) {
                        CocktailState.Empty
                    } else {
                        CocktailState.Success(cocktails)
                    }
                }
            } catch (e: Exception) {
                _state.value = CocktailState.Error("Erreur DB : ${e.message}")
            }
        }
    }

    fun observeFavorites() {
        viewModelScope.launch {
            try {
                dao.getFavoriteCocktails().collect { cocktails ->
                    _state.value = if (cocktails.isEmpty()) {
                        CocktailState.Empty
                    } else {
                        CocktailState.Success(cocktails)
                    }
                }
            } catch (e: Exception) {
                _state.value = CocktailState.Error("Erreur favoris : ${e.message}")
            }
        }
    }

    fun addCocktail(name: String, instructions: String) {
        if (name.isBlank() || instructions.isBlank()) return

        viewModelScope.launch {
            try {
                val cocktail = CocktailEntity(
                    name = name,
                    instructions = instructions,
                    imageUrl = null,
                    isFavorite = false,
                    createdAt = Date(),
                    updatedAt = null
                )
                dao.insert(cocktail)
            } catch (e: Exception) {
                _state.value = CocktailState.Error("Erreur ajout : ${e.message}")
            }
        }
    }

    fun fetchRandomCocktailFromApi() {
        viewModelScope.launch {
            try {
                val response = api.getRandomCocktail()
                val drink = response.drinks.firstOrNull()

                if (drink != null) {
                    val cocktail = CocktailEntity(
                        name = drink.strDrink,
                        instructions = drink.strInstructions ?: "Pas d'instructions disponibles",
                        imageUrl = drink.strDrinkThumb,
                        isFavorite = false,
                        createdAt = Date(),
                        updatedAt = null
                    )
                    dao.insert(cocktail)
                } else {
                    _state.value = CocktailState.Error("Aucun cocktail reçu depuis l'API.")
                }
            } catch (e: Exception) {
                _state.value = CocktailState.Error("Erreur API : ${e.message}")
            }
        }
    }

    fun toggleFavorite(cocktail: CocktailEntity) {
        viewModelScope.launch {
            try {
                dao.update(
                    cocktail.copy(
                        isFavorite = !cocktail.isFavorite,
                        updatedAt = Date()
                    )
                )
            } catch (e: Exception) {
                _state.value = CocktailState.Error("Erreur favori : ${e.message}")
            }
        }
    }

    fun deleteCocktail(cocktail: CocktailEntity) {
        viewModelScope.launch {
            try {
                dao.delete(cocktail)
            } catch (e: Exception) {
                _state.value = CocktailState.Error("Erreur suppression : ${e.message}")
            }
        }
    }

    suspend fun getCocktailById(id: Int): CocktailEntity? {
        return dao.getCocktailById(id)
    }
}