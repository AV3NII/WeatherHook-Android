package com.example.weatherhook.data.repository

import android.content.Context
import android.widget.Toast
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.ApiData
import com.example.weatherhook.data.models.ForecastData

class ForecastRepo() {

    fun addForecast(apiData: ApiData, context: Context, db: SQLiteHelper):Boolean{
        val didAddForecast = db.addForecast(apiData)

        return if(didAddForecast ){
            true
        }else{
            Toast.makeText(context,"Error trying to add forecast to DB", Toast.LENGTH_LONG).show()
            false
        }
    }

    fun updateForecast(apiData: ApiData, context: Context, db: SQLiteHelper):Boolean{
        val didDelete = db.deleteForecast()

        return if(didDelete){
            return addForecast(apiData,context,db)

        }else{
            Toast.makeText(context,"Error trying to remove forecast from DB", Toast.LENGTH_LONG).show()
            false
        }
    }

    fun getForecast(context: Context, db: SQLiteHelper):ForecastData{
        return db.getForecast()
    }

}