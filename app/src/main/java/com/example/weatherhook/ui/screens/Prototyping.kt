package com.example.weatherhook.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.weatherhook.data.models.Weather
import com.example.weatherhook.data.repository.WeatherHookRepo
import com.example.weatherhook.ui.components.WeatherForecast


class Prototyping : Fragment() {

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

    val listOfTemp = listOf<Weather>(
        Weather(0, 14f),
        Weather(1, 12f),
        Weather(3, 9f),
        Weather(5, 11f),
        Weather(0, 20f),
        Weather(0, 23f),
        Weather(3, 8f),
    )

    private val listOfDays = data.events[1].relevantDays.split(";")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {

            WeatherForecast(listOfTemp)
            
        }
    }

}