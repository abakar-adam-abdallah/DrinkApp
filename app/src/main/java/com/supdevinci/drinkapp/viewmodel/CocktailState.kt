/*
package com.supdevinci.drinkapp.viewmodel

import com.supdevinci.drinkapp.data.local.entities.CocktailEntity

sealed interface CocktailState {
    data object Loading : CocktailState
    data object Empty : CocktailState
    data class Success(val cocktails: List<CocktailEntity>) : CocktailState
    data class Error(val message: String) : CocktailState
}


 */

package com.supdevinci.drinkapp.viewmodel

import com.supdevinci.drinkapp.data.local.entities.CocktailEntity

sealed interface CocktailState {
    data object Loading : CocktailState
    data object Empty : CocktailState
    data class Success(val cocktails: List<CocktailEntity>) : CocktailState
    data class Error(val message: String) : CocktailState
}