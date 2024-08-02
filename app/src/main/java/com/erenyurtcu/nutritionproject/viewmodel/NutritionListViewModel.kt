package com.erenyurtcu.nutritionproject.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.erenyurtcu.nutritionproject.model.Nutrition
import com.erenyurtcu.nutritionproject.roomdb.NutritionDatabase
import com.erenyurtcu.nutritionproject.service.NutritionAPI
import com.erenyurtcu.nutritionproject.service.NutritionAPIService
import com.erenyurtcu.nutritionproject.util.SpcSharedPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NutritionListViewModel (application: Application) : AndroidViewModel(application) {


    val nutritions = MutableLiveData<List<Nutrition>>()
    val nutritionErrorMessage = MutableLiveData<Boolean>()
    val nutritionIsLoading = MutableLiveData<Boolean>()

    private val nutritionApiService = NutritionAPIService()
    private val spcSharedPreferences = SpcSharedPreferences(getApplication())

    private val updatedTime = 0.6 /* minute value*/ * 60 * 1000 * 1000 * 1000L

    fun refreshData(){
        val savedTime = spcSharedPreferences.getTime()
        if(savedTime != null && savedTime != 0L && System.nanoTime() - savedTime < updatedTime){
            //get data from room
        } else{
            getDataFromInternet()
        }
    }

    fun refreshDataFromInternet(){
        getDataFromInternet()
    }

    private fun getDataFromRoom(){
        nutritionIsLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val nutritionList = NutritionDatabase(getApplication()).nutritionDao().getAllNutrition()
            withContext(Dispatchers.Main){
                showNutritions(nutritionList)
                Toast.makeText(getApplication(),"Nutrition informations are taken from the Room.",Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getDataFromInternet(){
        nutritionIsLoading.value = true

        viewModelScope.launch(Dispatchers.IO) {
            val nutritionList = nutritionApiService.getData()
            withContext(Dispatchers.Main){
                nutritionIsLoading.value = false
                saveToRoom(nutritionList)
                Toast.makeText(getApplication(),"Nutrition informations are taken from the internet.", Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun showNutritions(nutritionList: List<Nutrition>) {

        nutritions.value = nutritionList
        nutritionErrorMessage.value = false
        nutritionIsLoading.value = false

    }

    private fun saveToRoom(nutritionList : List<Nutrition> ) {
        viewModelScope.launch {

            val dao = NutritionDatabase(getApplication()).nutritionDao()
            dao.deleteAllNutrition()
            val uuidList = dao.insertAll(*nutritionList.toTypedArray())
            var i = 0
            while (i < nutritionList.size) {
                nutritionList[i].uuid = uuidList[i].toInt()
                i = i + 1
            }
            showNutritions(nutritionList)
        }
        spcSharedPreferences.saveTime(System.nanoTime())
    }
}