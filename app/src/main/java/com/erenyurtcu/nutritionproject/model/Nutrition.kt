package com.erenyurtcu.nutritionproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class Nutrition(
    @ColumnInfo(name = "name")
    @SerializedName("name")
    val nutritionName : String?,
    @ColumnInfo(name = "calories")
    @SerializedName("calories")
    val nutritionCalorie : String?,
    @ColumnInfo(name = "carbohydrates")
    @SerializedName("carbohydrates")
    val nutritionCarbohydrate : String?,
    @ColumnInfo(name = "protein")
    @SerializedName("protein")
    val nutritionProtein : String?,
    @ColumnInfo(name = "fat")
    @SerializedName("fat")
    val nutritionFat : String?,
    @ColumnInfo(name = "image")
    @SerializedName("image")
    val nutritionImage : String?
) {
    @PrimaryKey(autoGenerate = true)
    var uuid : Int = 0
}
