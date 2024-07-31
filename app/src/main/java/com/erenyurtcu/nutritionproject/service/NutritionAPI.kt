package com.erenyurtcu.nutritionproject.service

import com.erenyurtcu.nutritionproject.model.Nutrition
import retrofit2.http.GET

interface NutritionAPI {

    //BASE URL >> https://raw.githubusercontent.com/
    //ENDPOINT >> erenyurtcu/nutrition-project/main/nutritions.json

    @GET("erenyurtcu/nutrition-project/main/nutritions.json")
    suspend fun getNutrition() : List<Nutrition>
}