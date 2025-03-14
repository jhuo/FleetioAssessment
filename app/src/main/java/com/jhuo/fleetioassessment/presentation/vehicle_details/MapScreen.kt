package com.jhuo.fleetioassessment.presentation.vehicle_details

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.jhuo.fleetioassessment.R

@Composable
fun MapScreen(latitude: Double, longitude: Double,) {
    val vehicleLocation = LatLng(latitude, longitude)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(vehicleLocation, 15f)
    }

    GoogleMap(
        modifier = Modifier.fillMaxWidth()
            .height(300.dp)
            .padding(horizontal = 8.dp, vertical = 16.dp),
        cameraPositionState = cameraPositionState,
    ) {
        Marker(
            state = rememberMarkerState(position = vehicleLocation),
            title = stringResource(R.string.vehicle_list)
        )
    }
}