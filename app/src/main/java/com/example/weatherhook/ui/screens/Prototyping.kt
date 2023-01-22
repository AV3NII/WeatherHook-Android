package com.example.weatherhook.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.weatherhook.data.repository.WeatherHookRepo
import com.example.weatherhook.ui.components.WeatherEventList
import com.example.weatherhook.ui.components.Weekdays


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

    private val listOfDays = data.events[1].relevantDays.split(";")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            Column(modifier = Modifier.fillMaxWidth(),horizontalAlignment = Alignment.CenterHorizontally) {

                Card(modifier = Modifier
                    .height(120.dp)
                    .padding(start = Dp(0f), top = Dp(20f), end = Dp(20f), bottom = Dp(10f)),
                    elevation = 5.dp,
                    shape = RoundedCornerShape(15.dp)) {
                    Column(modifier = Modifier.padding(start = Dp(15f), end = Dp(15f)), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Days of interest in the week", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Weekdays(listOfDays, 38)
                        }
                    }
                }

                WeatherEventList(data)



            }
            
        }
    }

}