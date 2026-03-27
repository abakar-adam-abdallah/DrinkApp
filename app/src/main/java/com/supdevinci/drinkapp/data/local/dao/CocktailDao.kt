/*

package com.supdevinci.drinkapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.supdevinci.drinkapp.data.local.entities.CocktailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Insert
    suspend fun insert(cocktail: CocktailEntity)

    @Update
    suspend fun update(cocktail: CocktailEntity)

    @Delete
    suspend fun delete(cocktail: CocktailEntity)

    @Query("SELECT * FROM cocktails")
    fun getAllCocktails(): Flow<List<CocktailEntity>>
}


 */

package com.supdevinci.drinkapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.supdevinci.drinkapp.data.local.entities.CocktailEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CocktailDao {

    @Insert
    suspend fun insert(cocktail: CocktailEntity)

    @Update
    suspend fun update(cocktail: CocktailEntity)

    @Delete
    suspend fun delete(cocktail: CocktailEntity)

    @Query("SELECT * FROM cocktails ORDER BY id DESC")
    fun getAllCocktails(): Flow<List<CocktailEntity>>

    @Query("SELECT * FROM cocktails WHERE isFavorite = 1 ORDER BY id DESC")
    fun getFavoriteCocktails(): Flow<List<CocktailEntity>>

    @Query("SELECT * FROM cocktails WHERE id = :id LIMIT 1")
    suspend fun getCocktailById(id: Int): CocktailEntity?
}