package com.jhuo.fleetioassessment.presentation.vehicle_details

import com.jhuo.fleetioassessment.domain.model.Vehicle

data class VehicleDetailsState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val vehicle: Vehicle? = null
)
