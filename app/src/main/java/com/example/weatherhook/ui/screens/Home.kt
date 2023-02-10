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
import com.example.weatherhook.data.models.Weather
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

    var listOfTemp = listOf<Weather>(
        Weather(0, 20f,true),
        Weather(1, 15f,true),
        Weather(4, 9f, true),
        Weather(4, 12f,true),
        Weather(1, 17f,true),
        Weather(0, 22f,true),
        Weather(0, 16f,true),
    )


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            Column(modifier = Modifier.verticalScroll(rememberScrollState()))  {
                LocationWeather(context = requireContext(),4, 15)
                WeatherForecast(listOfTemp)
                WeatherEventList(data, context = requireContext())
            }

            
        }
    }

}