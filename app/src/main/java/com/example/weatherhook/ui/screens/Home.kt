package com.example.weatherhook.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.weatherhook.data.models.ForcastData
import com.example.weatherhook.data.models.ForcastDay
import com.example.weatherhook.data.repository.WeatherHookRepo
import com.example.weatherhook.ui.components.LocationWeather
import com.example.weatherhook.ui.components.WeatherEventList
import com.example.weatherhook.ui.components.WeatherForecast


class Home : Fragment() {

    private lateinit var composeView: ComposeView

    val repo: WeatherHookRepo = WeatherHookRepo()
    val data = repo.loadAllData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    private var forcast = ForcastData(
        listOf(
            ForcastDay("MO","01d", 288.65f, 275.15f),
            ForcastDay("TU","03d", 273.64f, 275.15f),
            ForcastDay("WE","09n", 273.64f, 275.15f),
            ForcastDay("TH","50d", 273.64f, 275.15f),
            ForcastDay("FR","11n", 273.64f, 275.15f),
            ForcastDay("SA","13n", 273.64f, 275.15f),
            ForcastDay("SU","error", 273.64f, 275.15f),
        )
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()))  {
                LocationWeather(context = requireContext(),4, 15)
                WeatherForecast(forcast)
                WeatherEventList(data, context = requireContext())
            }

            
        }
    }

}