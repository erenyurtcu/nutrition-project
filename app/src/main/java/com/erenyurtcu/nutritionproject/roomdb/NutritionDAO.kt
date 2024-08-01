package com.erenyurtcu.nutritionproject.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.erenyurtcu.nutritionproject.model.Nutrition

@Dao
interface NutritionDAO {

    @Insert
    suspend fun insertAll (vararg nutrition: Nutrition) : List<Long>

    @Query("SELECT * FROM nutrition")
    suspend fun getAllNutrition() : List<Nutrition>

    @Query("SELECT * FROM nutrition WHERE uuid = :nutritionId")
    suspend fun getNutrition(nutritionId : Int) : Nutrition

    @Query("DELETE FROM nutrition")
    suspend fun deleteAllNutrition(nutrition : Nutrition)

}