package com.example.weatherhook.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.material.Switch
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.weatherhook.R
import com.example.weatherhook.Weekdays
import com.example.weatherhook.data.repository.WeatherHookRepo


class Prototyping : Fragment() {

    private lateinit var composeView: ComposeView

    val repo: WeatherHookRepo = WeatherHookRepo()
    val data = repo.loadAllData().events[1]

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

    val listOfDays = data.relevantDays
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        composeView.setContent {
            Column(modifier = Modifier.fillMaxWidth()) {

                Card(modifier = Modifier.padding(10.dp), elevation = 5.dp, shape = RoundedCornerShape(15.dp)) {
                    Column(modifier = Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(text = "Days of interest in the week", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Row(horizontalArrangement = Arrangement.SpaceBetween) {
                            Weekdays(listOfDays, 38)
                        }
                    }
                }




                Card(modifier = Modifier
                    .padding(10.dp)
                    .height(120.dp), elevation = 5.dp, shape = RoundedCornerShape(15.dp)) {
                    Row() {
                        Surface(shape = RoundedCornerShape(15.dp), modifier = Modifier
                            .height(120.dp)
                            .width(100.dp)) {
                            Box(modifier = Modifier.background(colorResource(id = R.color.mid_green))) {
                            }
                        }
                        Column(modifier = Modifier.padding(start = 10.dp)) {
                            Text(text = data.title, fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 10.dp, bottom = 5.dp, top = 10.dp))
                            Text(text = "Time to event: ${data.timeToEvent} Day(s)", modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp))
                            Weekdays(listOfDays, 20)
                        }
                        Column(modifier = Modifier.padding(end = 20.dp)) {
                                Switch(modifier = Modifier
                                    .fillMaxSize()
                                    .scale(1.3f), checked = data.active, onCheckedChange = {data.active = it})
                        }


                    }
                }
            }
            
        }
    }

}