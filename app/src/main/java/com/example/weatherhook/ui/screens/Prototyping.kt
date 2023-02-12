package com.example.weatherhook.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.weatherhook.data.repository.WeatherHookRepo
import kotlinx.coroutines.*
import java.util.*

class Prototyping : Fragment() {


    private lateinit var composeView: ComposeView

    val repo: WeatherHookRepo = WeatherHookRepo()
    val data = repo.loadAllData()

    
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


