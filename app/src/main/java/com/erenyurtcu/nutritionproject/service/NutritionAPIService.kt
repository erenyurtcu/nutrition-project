package com.erenyurtcu.nutritionproject.service

import com.erenyurtcu.nutritionproject.model.Nutrition
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NutritionAPIService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(NutritionAPI::class.java)

    suspend fun getData() : List<Nutrition> {
        return retrofit.getNutrition()
    }
}