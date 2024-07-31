package com.erenyurtcu.nutritionproject.model

import com.google.gson.annotations.SerializedName

data class Nutrition(
    @SerializedName("name")
    val nutritionName : String?,
    @SerializedName("calories")
    val nutritionCalorie : String?,
    @SerializedName("carbohydrates")
    val nutritionCarbohydrate : String?,
    @SerializedName("protein")
    val nutritionProtein : String?,
    @SerializedName("fat")
    val nutritionFat : String?,
    @SerializedName("image")
    val nutritionImage : String?
)
