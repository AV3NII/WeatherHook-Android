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
            event.timeToEvent += 1
            event.location = db.getLocationWithId(event.eventId)
            event.triggers = db.getAllTriggersWithId(event.eventId)
        }
        return weatherHookEventList
    }

    fun updateEvent(weatherHookEvent: WeatherHookEvent, context: Context, db: SQLiteHelper):Boolean{
        val didUpdateEvent = db.updateEvent(weatherHookEvent)
        val didUpdateLocation = db.updateLocationWithId(weatherHookEvent.location, weatherHookEvent.eventId)
        var didUpdateTriggers = true

        val storedTriggerIdsWithId = db.getIdsFromTriggersWithEventId(weatherHookEvent.eventId)

        //checks all conditions for adding updating and deleting inorder to portray the new trigger state in the db

        if (storedTriggerIdsWithId.size == weatherHookEvent.triggers.size){
            for ((index, trigger) in weatherHookEvent.triggers.withIndex()){
                didUpdateTriggers = didUpdateTriggers and db.updateTriggerWithId(trigger,storedTriggerIdsWithId[index])
            }
        }else if (storedTriggerIdsWithId.size < weatherHookEvent.triggers.size){
            storedTriggerIdsWithId.forEachIndexed { index, storedID ->
                didUpdateTriggers = didUpdateTriggers and db.updateTriggerWithId(weatherHookEvent.triggers[index],storedID)
            }

            for (index in (storedTriggerIdsWithId.size) until (weatherHookEvent.triggers.size )){
                didUpdateTriggers = didUpdateTriggers and db.addTrigger(weatherHookEvent.triggers[index], weatherHookEvent.eventId)
            }

        }else{
            weatherHookEvent.triggers.forEachIndexed { index, trigger ->
                didUpdateTriggers = didUpdateTriggers and db.updateTriggerWithId(trigger,storedTriggerIdsWithId[index])
            }

            for (index in (weatherHookEvent.triggers.size ) until (storedTriggerIdsWithId.size)){
                didUpdateTriggers = didUpdateTriggers and db.deleteTriggerWithId(storedTriggerIdsWithId[index])
            }
        }

        return if (didUpdateEvent && didUpdateLocation && didUpdateTriggers) true
        else{
            Toast.makeText(context,"Error trying to update event info in DB",Toast.LENGTH_LONG).show()
            false
        }
    }

    fun deleteEventWithId(eventId:Int,context: Context, db: SQLiteHelper):Boolean{
        val didDeleteEvent = db.deleteEventWithId(eventId)
        val didDeleteLocation = db.deleteLocationWithId(eventId)
        val didDeleteTriggers = db.deleteTriggersWithId(eventId)

        return if(didDeleteEvent && didDeleteLocation && didDeleteTriggers){
            true
        }else{
            Toast.makeText(context,"Error trying to delete event from DB",Toast.LENGTH_LONG).show()
            false
        }
    }

}