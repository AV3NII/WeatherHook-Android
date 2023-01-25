package com.example.weatherhook.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.weatherhook.data.models.Weather
import com.example.weatherhook.data.repository.WeatherHookRepo
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
        Weather(0, 20f),
        Weather(1, 15f),
        Weather(4, 9f),
        Weather(4, 12f),
        Weather(1, 17f),
        Weather(0, 22f),
        Weather(0, 16f),
    )


    private val listOfDays = data.events[1].relevantDays.split(";")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            Column() {
                WeatherForecast(listOfTemp)
                WeatherEventList(data, context = requireContext())
            }

            
        }
    }

}