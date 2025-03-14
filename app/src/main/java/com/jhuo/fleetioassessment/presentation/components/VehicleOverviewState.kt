package com.jhuo.fleetioassessment.presentation.components

import com.jhuo.fleetioassessment.domain.model.Vehicle

data class VehicleOverviewState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val vehicle: Vehicle? = null
)
