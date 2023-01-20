package com.example.weatherhook.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.weatherhook.data.models.Weather
import com.example.weatherhook.data.models.WeatherHookEvent
import com.example.weatherhook.data.models.WeatherHookEventList


class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{


        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "WeatherHookBook.db"

        //Tables
        private const val TABLE_WEATHER_HOOK_EVENTS = "WeatherHookEvents"
        private const val TABLE_LOCATIONS = "TableLocations"
        private const val TABLE_TRIGGERS = "TableTriggers"

        //Table Weather Hook Events columns
        private const val EVENT_ID = "eventId"
        private const val ACTIVE = "active"
        private const val TITLE = "title"
        private const val TIME_TO_EVENT = "timeToEvent"
        private const val RELEVANT_DAYS = "relevantDays"

        //Table location columns
        private const val LOCATION_ID = "locationId"
        private const val LATITUDE = "latitude"
        private const val LONGITUDE = "longitude"
        private const val EVENT_KEY = "eventKey"

        //Tables triggers columns
        private const val TRIGGER_ID = "triggerId"
        private const val PHENOMENON = "phenomenon"
        private const val CORRESPONDING_INTENSITY = "correspondingIntensity"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableWeatherHookEvents = "CREATE TABLE $TABLE_WEATHER_HOOK_EVENTS (" +
            "$EVENT_ID INTEGER PRIMARY KEY ,"+
            "$ACTIVE BOOL NOT NULL,"+
            "$TITLE VARCHAR(20) NOT NULL,"+
            "$TIME_TO_EVENT INT NOT NULL,"+
            "$RELEVANT_DAYS VARCHAR(20) NOT NULL)"

        val createTableTriggers = "CREATE TABLE $TABLE_TRIGGERS (" +
            "$TRIGGER_ID INTEGER NOT NULL PRIMARY KEY,"+
            "$PHENOMENON INTEGER NOT NULL,"+
            "$CORRESPONDING_INTENSITY FLOAT NOT NULL,"+
            "$EVENT_KEY INTEGER NOT NULL,"+
            "FOREIGN KEY ($EVENT_KEY) REFERENCES $TABLE_WEATHER_HOOK_EVENTS($EVENT_ID))"

        val createTableLocations = "CREATE TABLE $TABLE_LOCATIONS (" +
            "$LOCATION_ID INTEGER PRIMARY KEY ,"+
            "$LATITUDE FLOAT NOT NULL,"+
            "$LONGITUDE FLOAT NOT NULL,"+
            "$EVENT_KEY INTEGER UNIQUE NOT NULL,"+
            "FOREIGN KEY ($EVENT_KEY) REFERENCES $TABLE_WEATHER_HOOK_EVENTS($EVENT_ID))"


        db?.execSQL(createTableWeatherHookEvents)
        db?.execSQL(createTableLocations)
        db?.execSQL(createTableTriggers)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_WEATHER_HOOK_EVENTS")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_LOCATIONS")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_TRIGGERS")
            onCreate(db)
        }
    }

    //ADD Functions

    fun addEvent(event: WeatherHookEvent):Boolean{
        val db = writableDatabase

        db.beginTransaction()

        try {
            val query = "INSERT INTO $TABLE_WEATHER_HOOK_EVENTS($ACTIVE, $TITLE, $TIME_TO_EVENT, $RELEVANT_DAYS) VALUES (${event.active},${event.title}, ${event.timeToEvent}, ${event.relevantDays});"

            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add user to database")
        } finally {
            db.endTransaction()
        }
        return false
    }

    fun addLocation(location: Pair<Float,Float>, eventId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()

        try {
            val query = "INSERT INTO $TABLE_LOCATIONS($LONGITUDE, $LATITUDE;$EVENT_KEY) VALUES (${location.first},${location.second}, $eventId)"
            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add user to database")

        } finally {
            db.endTransaction()
        }
        return false
    }

    fun addTriggers(triggers: List<Weather>, eventId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()

        try {
            var query = ""
            for (trigger in triggers){
                query += "INSERT INTO $TABLE_TRIGGERS($PHENOMENON, $CORRESPONDING_INTENSITY,$EVENT_KEY) VALUES (${trigger.weatherPhenomenon},${trigger.correspondingIntensity},$eventId);"
            }
            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add user to database")
        } finally {
            db.endTransaction()
        }
        return false
    }



    fun getLocationsWithId(eventId: Int):Pair<Float,Float>{
        val db = writableDatabase
        val location:Pair<Float,Float>

        db.beginTransaction()

        try {
            val query = "SELECT * FROM $TABLE_LOCATIONS where $EVENT_KEY = $eventId;"
            val response = db.rawQuery(query,null)
            if (response.moveToFirst()){

                location = Pair(response.getFloat(2), response.getFloat(3))
                response.close()
                return location

            }
            db.setTransactionSuccessful()


        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add user to database")
        } finally {
            db.endTransaction()
        }
        return Pair(-1f,-1f)
    }
    fun getAllTriggersWithId(eventId: Int):MutableList<Weather>{
        val db = writableDatabase
        val triggers:MutableList<Weather> = listOf<Weather>().toMutableList()
        db.beginTransaction()

        try {
            val query = "SELECT * FROM $TABLE_TRIGGERS where EVENT_KEY=$eventId;"
            val response = db.rawQuery(query,null)
            if (response.moveToFirst()){

                do {
                    triggers.add(Weather(response.getInt(1),response.getFloat(2)))

                }while (response.moveToNext())
            }
            response.close()
            db.setTransactionSuccessful()

            return triggers
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add user to database")
        } finally {
            db.endTransaction()
        }
        return triggers
    }

    fun getAllEvents():WeatherHookEventList{
        val db = writableDatabase
        val eventsList= WeatherHookEventList(listOf<WeatherHookEvent>().toMutableList())
        db.beginTransaction()

        try {
            val query = "SELECT * FROM $TABLE_WEATHER_HOOK_EVENTS"
            val response = db.rawQuery(query,null)
            if (response.moveToFirst()){
                do{
                    eventsList.events.add(WeatherHookEvent(response.getInt(0),response.getString(1).toBoolean(),response.getString(2),getLocationsWithId(response.getInt(0)),response.getInt(3), response.getString(4),getAllTriggersWithId(response.getInt(0))))
                }while(response.moveToNext())

            }
            response.close()
            db.setTransactionSuccessful()

            return eventsList
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add user to database")
        } finally {
            db.endTransaction()
        }
        return eventsList
    }


}



