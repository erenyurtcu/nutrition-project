package com.erenyurtcu.nutritionproject.util

import android.content.Context
import android.content.SharedPreferences
import com.erenyurtcu.nutritionproject.roomdb.NutritionDatabase
import java.sql.Time

class SpcSharedPreferences {

    companion object {

        private val TIME = "time"
        private var sharedPreferences : SharedPreferences? = null

        @Volatile
        private var instance: SpcSharedPreferences? = null

        private val lock = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(lock) {
            instance ?: createSpcSharedPreferences(context).also {
                instance = it
            }
        }

        private fun createSpcSharedPreferences(context: Context) : SpcSharedPreferences {
            sharedPreferences = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)
            return SpcSharedPreferences()
        }

        fun saveTime(time: Long) {
            sharedPreferences?.edit()?.putLong(TIME,time)?.apply()
        }

        fun getTime() = sharedPreferences?.getLong(TIME,0)
    }
}