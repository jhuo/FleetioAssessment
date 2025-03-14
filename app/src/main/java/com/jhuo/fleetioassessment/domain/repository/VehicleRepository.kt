package com.jhuo.fleetioassessment.domain.repository

import androidx.paging.PagingData
import com.jhuo.fleetioassessment.data.model.local.vehicle.VehicleEntity
import com.jhuo.fleetioassessment.domain.model.Vehicle
import com.jhuo.fleetioassessment.util.Resource
import kotlinx.coroutines.flow.Flow

interface VehicleRepository {
    suspend fun getVehicleOverview(id: Int): Flow<Resource<Vehicle>>

    fun getVehiclesPaged(name: String? = null): Flow<PagingData<VehicleEntity>>

    suspend fun updateVehicleDetails(vehicleId: Int, updateRequest: Map<String, String>): Resource<Vehicle>
}