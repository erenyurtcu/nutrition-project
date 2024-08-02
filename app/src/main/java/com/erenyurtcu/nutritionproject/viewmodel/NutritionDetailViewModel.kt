package com.erenyurtcu.nutritionproject.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.erenyurtcu.nutritionproject.model.Nutrition
import com.erenyurtcu.nutritionproject.roomdb.NutritionDatabase
import kotlinx.coroutines.launch
import java.util.UUID

class NutritionDetailViewModel (application: Application) : AndroidViewModel(application)  {

    val nutritionLiveData = MutableLiveData<Nutrition>()
    fun getDataRoom(uuid: Int){
        viewModelScope.launch {
            val dao = NutritionDatabase(getApplication()).nutritionDao()
            val nutrition = dao.getNutrition(uuid)
            nutritionLiveData.value = nutrition
        }
    }
}