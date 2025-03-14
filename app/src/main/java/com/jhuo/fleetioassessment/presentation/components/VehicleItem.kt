package com.jhuo.fleetioassessment.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.jhuo.fleetioassessment.domain.model.Vehicle
import com.jhuo.fleetioassessment.R
import com.jhuo.fleetioassessment.util.Screen

@Composable
fun VehicleItem(
    vehicle: Vehicle,
    navHostController: NavHostController
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { navHostController.navigate(Screen.VehicleOverview.rout + "/${vehicle.id}") },
        elevation = CardDefaults
            .cardElevation(4.dp))
    {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = if (vehicle.imageUrl.isEmpty()) {
                    painterResource(id = R.drawable.vehicle_place_holder)
                } else {
                    rememberAsyncImagePainter(vehicle.imageUrl)
                },
                contentDescription = vehicle.name,
                modifier = Modifier.size(64.dp)
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(text = vehicle.name, fontSize = 18.sp)
                val vehicleDetails = listOfNotNull(vehicle.year.takeIf { it > 0 }?.toString(), vehicle.make, vehicle.model).joinToString(" ")
                if (vehicleDetails.isNotEmpty()) {
                    Text(text = vehicleDetails, fontSize = 14.sp, color = Color.Gray)
                }
                Row(modifier = Modifier.padding(top = 4.dp)) {

                    if(vehicle.status.isNotEmpty()) {
                        Chip(
                            status = vehicle.status,
                            color = when (vehicle.statusColor) {
                                "green" -> Color.Green
                                "red" -> Color.Red
                                "blue" -> Color.Blue
                                "orange" -> colorResource(R.color.orange)
                                else -> Color.Gray
                            }
                        )
                    }
                    if(vehicle.type.isNotEmpty()) {
                        Chip(
                            status = vehicle.type,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun Chip(status: String, color: Color) {
    Box(
        modifier = Modifier
            .background(color, RoundedCornerShape(4.dp))
            .padding(horizontal = 8.dp)
    ) {
        Text(
            text = status,
            fontSize = 12.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )
    }
}