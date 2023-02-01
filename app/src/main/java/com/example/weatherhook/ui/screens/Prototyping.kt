package com.example.weatherhook.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.coroutineScope
import com.example.weatherhook.data.api.OpenWeatherApi
import com.example.weatherhook.data.repository.WeatherHookRepo
import kotlinx.coroutines.*
import java.util.*

class Prototyping : Fragment() {


    private lateinit var composeView: ComposeView

    val repo: WeatherHookRepo = WeatherHookRepo()
    val data = repo.loadAllData()
    val apiService = OpenWeatherApi()

    
    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        lifecycle.coroutineScope.launch{
            val ans = apiService.getWeatherForcast(48.215748f, 12.125494f, 7).await()
            Log.d("shit",ans.toString())

        }
        return ComposeView(requireContext()).also {
            composeView = it

        }
    }






    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        composeView.setContent {

            Text(text = "asdf")
            

        }


    }

}


