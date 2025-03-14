package com.jhuo.fleetioassessment.data.model.local.vehicle

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jhuo.fleetioassessment.util.Constants.VEHICLE_LIST_TABLE

@Entity(tableName = VEHICLE_LIST_TABLE)
data class VehicleEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val type: String,
    val status: String,
    val licensePlate: String,
    val make: String,
    val model: String,
    val color: String,
    val primaryMeterUnit: String,
    val primaryMeterValue: String,
    val secondaryMeterUnit: String,
    val secondaryMeterValue: String,
    val statusColor: String,
    val vin: String,
    val year: Int,
    val driverFullName: String,
    val driverImageUrl: String,
    val latitude: Double?,
    val longitude: Double?,
    val commentsCount: Int
)
