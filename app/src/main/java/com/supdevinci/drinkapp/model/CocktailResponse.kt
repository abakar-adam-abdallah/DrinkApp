/*
package com.supdevinci.drinkapp.model

data class CocktailResponse(
    val drinks: List<Drink>
)

data class Drink(
    val idDrink: String,
    val strDrink: String,
    val strInstructions: String,
    val strDrinkThumb: String
)

 */

package com.supdevinci.drinkapp.model

data class CocktailResponse(
    val drinks: List<Drink>
)

data class Drink(
    val idDrink: String,
    val strDrink: String,
    val strInstructions: String?,
    val strDrinkThumb: String?
)