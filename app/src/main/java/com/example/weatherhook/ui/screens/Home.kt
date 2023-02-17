package com.example.weatherhook.ui.screens

import android.Manifest
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.ForecastData
import com.example.weatherhook.data.models.WeatherHookEventList
import com.example.weatherhook.data.repository.EventRepo
import com.example.weatherhook.data.repository.ForecastRepo
import com.example.weatherhook.ui.components.LocationWeather
import com.example.weatherhook.ui.components.WeatherEventList
import com.example.weatherhook.ui.components.WeatherForecast


class Home : Fragment() {

    private lateinit var composeView: ComposeView

    val repo = EventRepo()
    lateinit var data:WeatherHookEventList

    lateinit var db: SQLiteHelper

    val forecastRepo = ForecastRepo()
    lateinit var forecast:ForecastData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = SQLiteHelper(requireContext())
        data = repo.getAllEvents(db)

        forecast = forecastRepo.getForecast(db)
        Log.e("shit", "Home shit ${forecast.toString()}")
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).also {
            composeView = it
        }
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locationPermission = ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION)

        composeView.setContent {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()))  {
                LocationWeather(context = requireContext(),forecast.data[0])
                WeatherForecast(forecast)
                if (data.events.size > 0) WeatherEventList(data, context = requireContext(),db)

            }

            
        }
    }

}