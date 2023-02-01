package com.example.weatherhook.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState



@Composable
fun MapsHook(posLat: Double, posLong: Double) {
    val pos = LatLng(posLat, posLong)
    val clickedPosition = remember { mutableStateOf<LatLng?>(pos) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(pos, 10f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize().height(250.dp),
        cameraPositionState = cameraPositionState,
        onMapLongClick = { latLng ->
            clickedPosition.value = latLng
        }
    ) {
        Marker(state = MarkerState(position = clickedPosition.value!!))
    }
}