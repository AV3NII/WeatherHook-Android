package com.example.weatherhook.data.repository

import android.content.Context
import android.widget.Toast
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.WeatherHookEvent
import com.example.weatherhook.data.models.WeatherHookEventList

class DatabaseRepo() {
    fun addNewWeatherHookToDb(weatherHookEvent: WeatherHookEvent,context: Context,db:SQLiteHelper):Boolean{
        val didAddEvent = db.addEvent(weatherHookEvent)

        val newId = db.getLastIdFromEventsTable()

        val didAddTriggers = db.addTriggers(weatherHookEvent.triggers,newId)
        val didAddLocation = db.addLocation(weatherHookEvent.location,newId)

        return if(didAddEvent && didAddLocation && didAddTriggers){
            true
        }else{
            Toast.makeText(context,"Error trying to insert event to DB",Toast.LENGTH_LONG).show()
            false
        }

    }

    fun getAllEvents(db: SQLiteHelper):WeatherHookEventList{
        val weatherHookEventList = db.getEvents()
        for (event in weatherHookEventList.events){
            event.location = db.getLocationWithId(event.eventId)
            event.triggers = db.getAllTriggersWithId(event.eventId)
        }
        return weatherHookEventList
    }

    fun updateEvent(weatherHookEvent: WeatherHookEvent, context: Context, db: SQLiteHelper):Boolean{
        val didUpdateEvent = db.updateEvent(weatherHookEvent)

        return if (didUpdateEvent) true
        else{
            Toast.makeText(context,"Error trying to update event in DB",Toast.LENGTH_LONG).show()
            false
        }
    }

}