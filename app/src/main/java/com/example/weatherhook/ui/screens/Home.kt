package com.example.weatherhook.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.ForecastData
import com.example.weatherhook.data.models.ForecastDay
import com.example.weatherhook.data.models.WeatherHookEventList
import com.example.weatherhook.data.repository.DatabaseRepo
import com.example.weatherhook.ui.components.LocationWeather
import com.example.weatherhook.ui.components.WeatherEventList
import com.example.weatherhook.ui.components.LocationWeather
import com.example.weatherhook.ui.components.WeatherForecast


class Home : Fragment() {

    private lateinit var composeView: ComposeView

    val repo= DatabaseRepo()
    lateinit var data:WeatherHookEventList

    lateinit var db: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = SQLiteHelper(requireContext())
        data = repo.getAllEvents(db)
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

    private var forcast = ForecastData(
        listOf(
            ForecastDay("MO","01d", 288.65f, 275.15f),
            ForecastDay("TU","03d", 273.64f, 275.15f),
            ForecastDay("WE","09n", 273.64f, 275.15f),
            ForecastDay("TH","50d", 273.64f, 275.15f),
            ForecastDay("FR","11n", 273.64f, 275.15f),
            ForecastDay("SA","13n", 273.64f, 275.15f),
            ForecastDay("SU","error", 273.64f, 275.15f),
        )
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()))  {
                LocationWeather(context = requireContext(),4, 15)
                WeatherForecast(forcast)
                if (data.events.size > 0) WeatherEventList(data, context = requireContext(),db)

            }

            
        }
    }

}