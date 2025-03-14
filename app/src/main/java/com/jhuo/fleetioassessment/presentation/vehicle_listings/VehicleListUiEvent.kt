package com.jhuo.fleetioassessment.presentation.vehicle_listings

sealed class VehicleOverviewEvent {
    data class UpdateVehicleDetails(
        val vin: String,
        val licensePlate: String
    ) : VehicleOverviewEvent()
}