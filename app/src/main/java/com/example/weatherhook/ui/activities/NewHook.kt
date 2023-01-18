package com.example.weatherhook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.weatherhook.data.repository.WeatherHookRepo
import com.example.weatherhook.databinding.FragmentTest1Binding


class NewHook : Fragment(R.layout.fragment_test1) {

    private var _binding: FragmentTest1Binding? = null
    private val binding: FragmentTest1Binding
        get() = _binding!!

    val repo = WeatherHookRepo()
    val data = repo.loadAllData()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {



        _binding = FragmentTest1Binding.inflate(inflater, container, false)
        binding.composeView.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                Card (
                    elevation = 5.dp,
                    shape = RoundedCornerShape(15.dp),
                    modifier = Modifier.padding(10.dp).fillMaxWidth(1f)
                ){
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Text(text = data.events[0].title)
                        Text(text = data.events[0].active.toString())
                        Text(text = "Wow")
                    }

                }
            }
        }
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

/*

@Composable
fun NewHook() {

    Card (
        elevation = 5.dp,
        backgroundColor = Color.DarkGray,
        border = BorderStroke(2.dp, Color.Black),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.padding(10.dp).fillMaxWidth(1f)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Hello")
            Text(text = "This is a test")
            Text(text = "Wow")
        }

    }
    
}

*/