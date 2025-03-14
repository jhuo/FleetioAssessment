package com.jhuo.fleetioassessment.data.mappers

import com.jhuo.fleetioassessment.data.model.local.vehicle.VehicleEntity
import com.jhuo.fleetioassessment.data.remote.dto.vehicle.VehicleDto
import com.jhuo.fleetioassessment.domain.model.Vehicle

fun VehicleDto.toVehicleEntity(): VehicleEntity {
    val latitude = currentLocationEntry?.geolocation?.latitude
    val longitude = currentLocationEntry?.geolocation?.longitude
    val driverFullName = driver?.full_name
    val driverImageUrl = driver?.default_image_url
    return VehicleEntity(
        id = id,
        name = name,
        imageUrl = imageUrl ?: "",
        type = type ?: "",
        status = status ?: "",
        licensePlate = licensePlate ?: "",
        make = make ?: "",
        model = model ?: "",
        color = color ?: "",
        primaryMeterUnit = primaryMeterUnit ?: "",
        primaryMeterValue = primaryMeterValue ?: "",
        secondaryMeterUnit = secondaryMeterUnit ?: "",
        secondaryMeterValue = secondaryMeterValue ?: "",
        statusColor = statusColor ?: "",
        vin = vin ?: "",
        year = year ?: 0,
        driverFullName = driverFullName ?: "",
        driverImageUrl = driverImageUrl ?: "",
        latitude = latitude,
        longitude = longitude,
        commentsCount = commentsCount ?: 0
    )
}

fun VehicleEntity.toVehicle(): Vehicle {
    return Vehicle(
        id = id,
        name = name,
        imageUrl = imageUrl,
        type = type,
        status = status,
        licensePlate = licensePlate,
        make = make,
        model = model,
        color = color,
        primaryMeterUnit = primaryMeterUnit,
        primaryMeterValue = primaryMeterValue,
        secondaryMeterUnit = secondaryMeterUnit,
        secondaryMeterValue = secondaryMeterValue,
        statusColor = statusColor,
        vin = vin,
        year = year,
        driverFullName = driverFullName,
        driverImageUrl = driverImageUrl,
        latitude = latitude,
        longitude = longitude,
        commentsCount = commentsCount
    )
}