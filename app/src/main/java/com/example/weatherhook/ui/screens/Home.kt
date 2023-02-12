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
import com.example.weatherhook.data.api.callApi
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
        var test = "ERROR1"


        callApi(48.208f,12.122f,7,requireContext()) { result ->
            if (result.cod == "200") {
                test = result.toString()
                Log.d("test", test)
            } else {
                test = result.city.name
                Log.d("test", test)
            }
        }



        composeView.setContent {
            val refresh = remember {
                mutableStateOf(1)
            }
            Column(modifier = Modifier.verticalScroll(rememberScrollState()))  {
                LocationWeather(context = requireContext(),4, 15)
                WeatherForecast(listOfTemp)
                WeatherEventList(data, context = requireContext())
                Text(text = test, fontSize = 20.sp)
                Button(onClick = {refresh.value += 1}) {
                }
                Text(text = refresh.value.toString())
                Spacer(modifier = Modifier.height(100.dp))
            }

            
        }
    }

}