package com.jhuo.fleetioassessment.presentation.components

sealed class VehicleOverviewEvent {
    data class UpdateVehicleDetails(
        val vin: String,
        val licensePlate: String
    ) : VehicleOverviewEvent()
}