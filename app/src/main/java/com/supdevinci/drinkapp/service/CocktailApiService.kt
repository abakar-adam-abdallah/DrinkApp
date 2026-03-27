package com.supdevinci.drinkapp.service

import com.supdevinci.drinkapp.model.CocktailResponse
import retrofit2.http.GET

interface CocktailApiService {

    @GET("random.php")
    suspend fun getRandomCocktail(): CocktailResponse
}