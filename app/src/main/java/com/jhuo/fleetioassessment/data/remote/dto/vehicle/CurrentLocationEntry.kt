package com.jhuo.fleetioassessment.data.remote.dto.vehicle

data class CurrentLocationEntry(
    val address: String,
    val contact_id: Any,
    val created_at: String,
    val date: String,
    val geolocation: Geolocation,
    val id: Int,
    val is_current: Boolean,
    val item_id: Int,
    val item_type: String,
    val locatable_id: Int,
    val locatable_type: String,
    val location: String,
    val updated_at: String,
    val vehicle_id: Int
)