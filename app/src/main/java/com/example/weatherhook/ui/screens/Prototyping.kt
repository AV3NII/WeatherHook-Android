package com.example.weatherhook.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.example.weatherhook.data.db.SQLiteHelper
import com.example.weatherhook.data.models.WeatherHookEventList
import com.example.weatherhook.data.repository.DatabaseRepo


class Prototyping : Fragment() {


    private lateinit var composeView: ComposeView

    val repo = DatabaseRepo()
    private lateinit var data:WeatherHookEventList
    private lateinit var db:SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = SQLiteHelper(requireContext())
        data = repo.getAllEvents(db)
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


        }


    }

}