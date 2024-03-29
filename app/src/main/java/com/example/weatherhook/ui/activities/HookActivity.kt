package com.example.weatherhook.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherhook.R
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.Weather
import com.example.weatherhook.data.models.WeatherHookEvent
import com.example.weatherhook.data.repository.EventRepo

class HookActivity : AppCompatActivity() {

    private var repo = EventRepo()
    private lateinit var data:WeatherHookEvent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hook)
        val db = SQLiteHelper(this)

        val eventId = intent.getIntExtra("currentEvent", -1)

        if (eventId >= 0){
            data = repo.getAllEvents(db).events[eventId]
        }else{
            data = WeatherHookEvent(
                eventId = eventId,
                active = true,
                title = "Error",
                location = Pair(52.52f,13.405f),
                timeToEvent = 3,
                relevantDays = "MO;TU;WE;TH;FR",
                triggers = listOf(Weather(weatherPhenomenon = 0, correspondingIntensity = 1f,true)).toMutableList()
            )
        }

        val action=supportActionBar
        if (eventId == -2){
            action!!.title = getString(R.string.newHook)
        }else action!!.title = data.title

    }


}