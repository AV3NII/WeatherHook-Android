package com.example.weatherhook.data.repository

import android.content.Context
import android.widget.Toast
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.ApiData
import com.example.weatherhook.data.models.CurrentWeather
import com.example.weatherhook.data.models.ForecastData

class ForcastRepo() {

    fun addForecast(currentWeather: CurrentWeather,apiData: ApiData, context: Context, db: SQLiteHelper):Boolean{
        val didAddCurrentWeather = db.addCurrentWeather(currentWeather)
        val didAddForecast = db.addForecast(apiData)

        return if(didAddForecast && didAddCurrentWeather){
            true
        }else{
            Toast.makeText(context,"Error trying to add forecast to DB", Toast.LENGTH_LONG).show()
            false
        }
    }

    fun updateForecast(currentWeather: CurrentWeather,apiData: ApiData, context: Context, db: SQLiteHelper):Boolean{
        val didDelete = db.deleteForecast()

        return if(didDelete){
            return addForecast(currentWeather,apiData,context,db)

        }else{
            Toast.makeText(context,"Error trying to remove forecast from DB", Toast.LENGTH_LONG).show()
            false
        }
    }

    fun getForecast(context: Context, db: SQLiteHelper):ForecastData{
        return db.getForecast()
    }

}