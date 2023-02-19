package com.example.weatherhook.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.weatherhook.data.models.*
import java.text.SimpleDateFormat
import java.util.*


class SQLiteHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object{


        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "WeatherHookBook.db"

        //Tables
        private const val TABLE_WEATHER_HOOK_EVENTS = "WeatherHookEvents"
        private const val TABLE_LOCATIONS = "TableLocations"
        private const val TABLE_TRIGGERS = "TableTriggers"

        private const val TABLE_FORECAST = "TableForecast"

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
        private const val CHECK_MORE_THAN = "checkMoreThan"
        private const val CORRESPONDING_INTENSITY = "correspondingIntensity"


        //Table forecast columns
        private const val DAY = "day"
        private const val ICON = "icon"
        private const val MIN_TEMP = "minTemp"
        private const val MAX_TEMP = "maxTemp"
        private const val LOCATION_NAME = "locationName"


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
                "$CHECK_MORE_THAN BOOL NOT NULL,"+
                "$EVENT_KEY INTEGER NOT NULL,"+
                "FOREIGN KEY ($EVENT_KEY) REFERENCES $TABLE_WEATHER_HOOK_EVENTS($EVENT_ID))"

        val createTableLocations = "CREATE TABLE $TABLE_LOCATIONS (" +
                "$LOCATION_ID INTEGER PRIMARY KEY ,"+
                "$LATITUDE FLOAT NOT NULL,"+
                "$LONGITUDE FLOAT NOT NULL,"+
                "$EVENT_KEY INTEGER UNIQUE NOT NULL,"+
                "FOREIGN KEY ($EVENT_KEY) REFERENCES $TABLE_WEATHER_HOOK_EVENTS($EVENT_ID))"



        val createTableForecast = "CREATE TABLE $TABLE_FORECAST (" +
                "$DAY VARCHAR(2) PRIMARY KEY,"+
                "$ICON VARCHAR(3) NOT NULL,"+
                "$MIN_TEMP FLOAT NOT NULL,"+
                "$MAX_TEMP FLOAT NOT NULL,"+
                "$LOCATION_NAME VARCHAR(50) NOT NULL"+
                ")"



        db?.execSQL(createTableWeatherHookEvents)
        db?.execSQL(createTableLocations)
        db?.execSQL(createTableTriggers)

        db?.execSQL(createTableForecast)


    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_WEATHER_HOOK_EVENTS")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_LOCATIONS")
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_TRIGGERS")

            db?.execSQL("DROP TABLE IF EXISTS $TABLE_FORECAST")
            onCreate(db)
        }
    }

    //ADD Functions

    fun addEvent(event: WeatherHookEvent):Boolean{
        val db = writableDatabase

        db.beginTransaction()

        try {
            val query = "INSERT INTO $TABLE_WEATHER_HOOK_EVENTS($ACTIVE, $TITLE, $TIME_TO_EVENT, $RELEVANT_DAYS) VALUES (${event.active}, \"${event.title}\", ${event.timeToEvent}, \"${event.relevantDays}\");"

            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add Event to database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }

    fun addLocation(location: Pair<Float,Float>, eventId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()

        try {
            val query = "INSERT INTO $TABLE_LOCATIONS( $LATITUDE,$LONGITUDE, $EVENT_KEY) VALUES (${location.first}, ${location.second}, $eventId);"
            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add Location to database")
            Log.e("DbHelper",e.message!!)

        } finally {
            db.endTransaction()
        }
        return false
    }

    fun addTriggers(triggers: List<Weather>, eventId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()

        try {

            triggers.forEach { trigger ->
                val query = "INSERT INTO $TABLE_TRIGGERS($PHENOMENON, $CORRESPONDING_INTENSITY, $CHECK_MORE_THAN, $EVENT_KEY) VALUES (${trigger.weatherPhenomenon},${trigger.correspondingIntensity},${trigger.checkMoreThan},$eventId);"
                db.execSQL(query)
            }

            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add Triggers to database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }

    fun addTrigger(trigger: Weather, eventId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()

        try {
            val query = "INSERT INTO $TABLE_TRIGGERS($PHENOMENON, $CORRESPONDING_INTENSITY, $CHECK_MORE_THAN, $EVENT_KEY) VALUES (${trigger.weatherPhenomenon},${trigger.correspondingIntensity},${trigger.checkMoreThan},$eventId);"
            db.execSQL(query)


            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add Trigger to database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }




    //GET Functions


    fun getLocationWithId(eventId: Int):Pair<Float,Float>{
        val db = readableDatabase
        val location:Pair<Float,Float>

        db.beginTransaction()

        try {
            val query = "SELECT * FROM $TABLE_LOCATIONS where $EVENT_KEY = $eventId;"
            val response = db.rawQuery(query,null)
            if (response.moveToFirst()){

                location = Pair(response.getFloat(1), response.getFloat(2))
                response.close()
                return location

            }
            db.setTransactionSuccessful()


        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to get Location from database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return Pair(-1f,-1f)
    }
    fun getAllTriggersWithId(eventId: Int):MutableList<Weather>{
        val db = readableDatabase
        val triggers:MutableList<Weather> = listOf<Weather>().toMutableList()
        db.beginTransaction()

        try {
            val query = "SELECT * FROM $TABLE_TRIGGERS where $EVENT_KEY = $eventId;"
            val response = db.rawQuery(query,null)
            if (response.moveToFirst()){

                do {
                    triggers.add(Weather(response.getInt(1),response.getFloat(2),response.getInt(3) > 0))

                }while (response.moveToNext())
            }
            response.close()
            db.setTransactionSuccessful()

            return triggers
        } catch (e: Exception) {

            Log.e("DbHelper", "Error while trying to get Triggers from database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return triggers
    }

    fun getEvents():WeatherHookEventList{
        val db = readableDatabase
        val eventsList= WeatherHookEventList(listOf<WeatherHookEvent>().toMutableList())
        db.beginTransaction()
        try {
            val query = "SELECT * FROM $TABLE_WEATHER_HOOK_EVENTS"
            val response = db.rawQuery(query,null)
            if (response.moveToFirst()){
                do{
                    val location = Pair(-1f,-1f)
                    val triggers = listOf<Weather>().toMutableList()

                    eventsList.events.add(WeatherHookEvent(response.getInt(0),response.getInt(1) > 0,response.getString(2),location,response.getInt(3), response.getString(4),triggers))
                }while(response.moveToNext())

            }
            response.close()
            db.setTransactionSuccessful()

            return eventsList
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to get all Events from database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return eventsList
    }

    fun getLastIdFromEventsTable():Int{
        val db = readableDatabase
        var result = -1
        db.beginTransaction()
        try {
            val query = "SELECT MAX($EVENT_ID) FROM $TABLE_WEATHER_HOOK_EVENTS"
            val response = db.rawQuery(query,null)
            if (response.moveToFirst()){

                result = response.getInt(0)
            }
            response.close()
            db.setTransactionSuccessful()

            return result
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to get highest Id in EventsTable from database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return result
    }

    fun getIdsFromTriggersWithEventId(eventId: Int):MutableList<Int>{
        val db = readableDatabase
        val result = mutableListOf<Int>()
        db.beginTransaction()
        try {
            val query = "SELECT $TRIGGER_ID FROM $TABLE_TRIGGERS WHERE $EVENT_KEY = $eventId"
            val response = db.rawQuery(query,null)

            if (response.moveToFirst()){
                do{
                    result += response.getInt(0)

                }while(response.moveToNext())
            }

            response.close()
            db.setTransactionSuccessful()

            return result
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to get trigger Ids from event from database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return result
    }


    //Update Functions
    fun updateEvent(weatherHookEvent: WeatherHookEvent):Boolean{
        val db = writableDatabase

        db.beginTransaction()
        try {
            val query = "UPDATE $TABLE_WEATHER_HOOK_EVENTS SET $ACTIVE = ${weatherHookEvent.active}, $TITLE = \"${weatherHookEvent.title}\", $TIME_TO_EVENT = ${weatherHookEvent.timeToEvent}, $RELEVANT_DAYS = \"${weatherHookEvent.relevantDays}\" WHERE $EVENT_ID = ${weatherHookEvent.eventId};"

            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to update event in database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }

    fun updateLocationWithId(location:Pair<Float,Float>,eventId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()
        try {
            val query = "UPDATE $TABLE_LOCATIONS SET $LATITUDE = ${location.first}, $LONGITUDE = ${location.second} WHERE $EVENT_KEY = ${eventId};"

            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to update event location in database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }

    fun updateTriggerWithId(trigger: Weather, triggerId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()
        try {
            val query = "UPDATE $TABLE_TRIGGERS SET $PHENOMENON = ${trigger.weatherPhenomenon}, $CORRESPONDING_INTENSITY = ${trigger.correspondingIntensity}, $CHECK_MORE_THAN = ${trigger.checkMoreThan} WHERE $TRIGGER_ID = $triggerId;"

            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to update event trigger in database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }


    //DELETE Functions

    fun deleteTriggerWithId(triggerId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()
        try {
            val query = "DELETE FROM $TABLE_TRIGGERS WHERE $TRIGGER_ID = $triggerId;"

            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to delete event trigger in database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }

    fun deleteTriggersWithId(eventId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()
        try {
            val query = "DELETE FROM $TABLE_TRIGGERS WHERE $EVENT_KEY = $eventId;"

            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to delete Triggers in database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }

    fun deleteEventWithId(eventId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()
        try {
            var query = "DELETE FROM $TABLE_WEATHER_HOOK_EVENTS WHERE $EVENT_ID = $eventId;"

            query += "DELETE FROM $TABLE_LOCATIONS WHERE $EVENT_KEY = $eventId;"
            query += "DELETE FROM $TABLE_TRIGGERS WHERE $EVENT_KEY = $eventId;"

            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to delete event in database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }

    fun deleteLocationWithId(eventId: Int):Boolean{
        val db = writableDatabase

        db.beginTransaction()
        try {
            val query = "DELETE FROM $TABLE_LOCATIONS WHERE $EVENT_KEY = $eventId;"

            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to delete location in database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }


    // Forcast Functions

    fun addForecast(apiData: ApiData,location:String):Boolean{
        val db = writableDatabase

        db.beginTransaction()

        try {
            var query = ""

            apiData.list.forEach { day ->
                val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
                val date = Date(day.dt * 1000)
                val selectedDate = sdf.format(date)

                val a = when (selectedDate){
                    "Monday" -> "MO"
                    "Tuesday" -> "TU"
                    "Wednesday" -> "WE"
                    "Thursday" -> "TH"
                    "Friday" -> "FR"
                    "Saturday" -> "SA"
                    "Sunday" -> "SU"
                    "Montag" -> "MO"
                    "Dienstag" -> "TU"
                    "Mittwoch" -> "WE"
                    "Donnerstag" -> "TH"
                    "Freitag" -> "FR"
                    "Samstag" -> "SA"
                    "Sonntag" -> "SU"
                    else -> ""
                }


                query = "INSERT INTO $TABLE_FORECAST($DAY, $ICON, $MIN_TEMP, $MAX_TEMP, $LOCATION_NAME) VALUES (\"${a}\", \"${day.weather[0].icon}\", ${day.temp.min}, ${day.temp.max}, \"${location}\");"
                db.execSQL(query)
            }


            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add Forecast to database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }


    fun getForecast(): ForecastData {
        val db = readableDatabase

        val forecast = ForecastData(data = listOf<ForecastDay>().toMutableList())
        db.beginTransaction()

        try {
            val query = "SELECT $DAY, $ICON, $MIN_TEMP, $MAX_TEMP, $LOCATION_NAME FROM $TABLE_FORECAST;"
            val response = db.rawQuery(query,null)
            if (response.moveToFirst()){
                do{
                    forecast.data.add(ForecastDay(response.getString(0), response.getString(1),response.getFloat(2),response.getFloat(3), response.getString(4)))
                }while(response.moveToNext())
            }
            response.close()
            db.setTransactionSuccessful()

            return forecast
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to add Forecast to database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return forecast
    }

    fun getLocation(): String {
        val db = readableDatabase

        var name = ""
        db.beginTransaction()

        try {
            val query = "SELECT $LOCATION_NAME FROM $TABLE_FORECAST;"
            val response = db.rawQuery(query,null)
            if (response.moveToFirst()){
                name = response.getString(0)
            }
            response.close()
            db.setTransactionSuccessful()

            return name
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to get location from database")
            Log.e("DbHelper",e.message!!)
            name = "Berlin"
        } finally {
            db.endTransaction()
        }
        return "Berlin"
    }


    fun deleteForecast():Boolean{
        val db = writableDatabase

        db.beginTransaction()

        try {
            val query = "DELETE FROM $TABLE_FORECAST"

            db.execSQL(query)
            db.setTransactionSuccessful()

            return true
        } catch (e: Exception) {
            Log.e("DbHelper", "Error while trying to delete Forecast from database")
            Log.e("DbHelper",e.message!!)
        } finally {
            db.endTransaction()
        }
        return false
    }


}



