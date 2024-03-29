package com.example.weatherhook.data.repository

import android.content.Context
import android.widget.Toast
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.ApiData
import com.example.weatherhook.data.models.ForecastData

class ForecastRepo() {

    fun addForecast(apiData: ApiData,location:String, context: Context, db: SQLiteHelper):Boolean{
        val didAddForecast = db.addForecast(apiData,location)

        return if(didAddForecast ){
            true
        }else{
            Toast.makeText(context,"Error trying to add forecast to DB", Toast.LENGTH_LONG).show()
            false
        }
    }

    fun updateForecast(apiData: ApiData,location:String, context: Context, db: SQLiteHelper):Boolean{
        val didDelete = db.deleteForecast()

        return if(didDelete){
            return addForecast(apiData,location,context,db)

        }else{
            Toast.makeText(context,"Error trying to remove forecast from DB", Toast.LENGTH_LONG).show()
            false
        }
    }

    fun getForecast(db: SQLiteHelper):ForecastData{
        return db.getForecast()
    }

    fun getName(db: SQLiteHelper):String{
        return db.getLocation()
    }

}