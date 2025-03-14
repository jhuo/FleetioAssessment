package com.jhuo.fleetioassessment.presentation.vehicle_details

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.jhuo.fleetioassessment.R
import com.jhuo.fleetioassessment.presentation.vehicle_listings.VehicleOverviewEvent
import com.jhuo.fleetioassessment.util.Screen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VehicleDetailsScreen(
    navController: NavHostController
) {
    val detailsViewModel = hiltViewModel<VehicleOverviewViewModel>()
    val detailsState = detailsViewModel.overviewState.collectAsState().value
    val vehicle = detailsState.vehicle

    // Bottom sheet state
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by remember { mutableStateOf(false) }

    var newVin by remember { mutableStateOf("") }
    var newLicensePlate by remember { mutableStateOf("") }

    val vinError by detailsViewModel.vinError.collectAsState()
    val licensePlateError by detailsViewModel.licensePlateError.collectAsState()

    val isButtonEnabled = vinError == null && licensePlateError == null
    val scrollState = rememberScrollState()

    if (vehicle == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            TopAppBar(
                title = { Text(vehicle.name) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                }
            )

            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = if (vehicle.imageUrl.isEmpty()) {
                            painterResource(id = R.drawable.vehicle_place_holder)
                        } else {
                            rememberAsyncImagePainter(vehicle.imageUrl)
                        },
                        contentDescription = null,
                        modifier = Modifier.size(50.dp)
                    )
                    Column(modifier = Modifier.padding(start = 16.dp)) {
                        Text(text = vehicle.name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        val vehicleDetails = listOfNotNull(
                            vehicle.year.takeIf { it > 0 }?.toString(),
                            vehicle.make,
                            vehicle.model
                        ).joinToString(" ")
                        Text(text = vehicleDetails, fontSize = 14.sp, color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                VehicleDetailItem(
                    label = stringResource(R.string.mileage),
                    value = "${vehicle.primaryMeterValue} ${vehicle.primaryMeterUnit}",
                )
                VehicleDetailItem(
                    label = stringResource(R.string.status),
                    value = vehicle.status,
                )

                Row(
                    Modifier
                        .clickable { navController.navigate(Screen.CommentList.rout + "/${vehicle.id}") }
                    ) {
                    VehicleDetailItem(
                        label = stringResource(R.string.comments),
                        value = "${vehicle.commentsCount}"
                    )

                    Icon(Icons.Default.ArrowForward, contentDescription = stringResource(R.string.forward))

                }

                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = stringResource(R.string.details),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    TextButton(
                        onClick = { showBottomSheet = true }
                    ) { Text(stringResource(R.string.edit)) }
                }

                Card(
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth().padding(8.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        VehicleDetailItem(
                            label = stringResource(R.string.vin),
                            value = vehicle.vin,
                        )
                        VehicleDetailItem(
                            label = stringResource(R.string.license_plate),
                            value = vehicle.licensePlate,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(text = stringResource(R.string.most_recent_location), fontSize = 18.sp, fontWeight = FontWeight.Bold)
                if (vehicle.latitude != null && vehicle.longitude != null) {
                    MapScreen(vehicle.latitude, vehicle.longitude)
                } else {
                    MapScreen(41.9333, -87.6667) // Mock Data For Demo Only
                }
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = { showBottomSheet = false },
            sheetState = sheetState,
            windowInsets = WindowInsets.ime
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .imePadding()
                    .verticalScroll(scrollState)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.update_details),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(1f)
                    )

                    TextButton(
                        onClick = {
                            scope.launch {
                                detailsViewModel.onEvent(
                                    VehicleOverviewEvent.UpdateVehicleDetails(
                                        vin = newVin,
                                        licensePlate = newLicensePlate
                                    )
                                )
                                sheetState.hide()
                                showBottomSheet = false
                            }
                        },
                        enabled = isButtonEnabled
                    ) {
                        Text(stringResource(R.string.save))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (vinError != null) {
                    Text(text = vinError!!, color = Color.Red, fontSize = 12.sp)
                } else {
                    Text(text = stringResource(R.string.vin_requirement), fontSize = 12.sp)
                }

                TextField(
                    value = newVin,
                    onValueChange = {
                        newVin = it
                        detailsViewModel.validateInputs(newVin, newLicensePlate)
                    },
                    maxLines = 1,
                    label = { Text(stringResource(R.string.vin)) },
                    modifier = Modifier.fillMaxWidth(),
                    isError = vinError != null
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (licensePlateError != null) {
                    Text(text = licensePlateError!!, color = Color.Red, fontSize = 12.sp)
                } else {
                    Text(text = stringResource(R.string.letter_numbers_only), fontSize = 12.sp)
                }
                TextField(
                    value = newLicensePlate,
                    onValueChange = {
                        newLicensePlate = it
                        detailsViewModel.validateInputs(newVin, newLicensePlate)
                    },
                    maxLines = 1,
                    label = { Text(stringResource(R.string.license_plate)) },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun VehicleDetailItem(label: String, value: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
        Text(text = label, fontSize = 14.sp, modifier = Modifier.weight(1f), color = Color.Gray)
        Text(text = value, fontSize = 14.sp)
    }
}
