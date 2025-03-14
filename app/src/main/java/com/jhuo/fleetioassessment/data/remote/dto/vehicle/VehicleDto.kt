package com.jhuo.fleetioassessment.data.remote.dto.vehicle

import com.google.gson.annotations.SerializedName

data class VehicleDto(
    val id: Int,
    val name: String,
    @SerializedName("default_image_url_small") val imageUrl: String?,
    @SerializedName("vehicle_type_name") val type: String?,
    @SerializedName("vehicle_status_name") val status: String?,
    @SerializedName("license_plate") val licensePlate: String?,
    val make: String?,
    val model: String?,
    val color: String?,
    @SerializedName("primary_meter_unit") val primaryMeterUnit: String?,
    @SerializedName("primary_meter_value") val primaryMeterValue: String?,
    @SerializedName("secondary_meter_unit") val secondaryMeterUnit: String?,
    @SerializedName("secondary_meter_value") val secondaryMeterValue: String?,
    @SerializedName("vehicle_status_color") val statusColor: String?,
    val vin: String?,
    val year: Int?,
    @SerializedName("current_location_entry") val currentLocationEntry: CurrentLocationEntry?,
    val driver: Driver?,
    @SerializedName("comments_count") val commentsCount: Int?,
)