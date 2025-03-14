package com.jhuo.fleetioassessment.data.model.local.vehicle

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface VehicleDao {
    @Upsert
    suspend fun upsertVehicleList(vehicleList: List<VehicleEntity>)

    @Upsert
    suspend fun upsertVehicle(vehicle: VehicleEntity)

    @Query("SELECT * FROM VEHICLE_LIST_TABLE ORDER BY id DESC")
    fun getVehiclesPagingSource(): PagingSource<Int, VehicleEntity>

    @Query("SELECT * FROM VEHICLE_LIST_TABLE WHERE LOWER(status) LIKE LOWER(:nameFilter) OR UPPER(:nameFilter) = status")
    suspend fun getVehicleList(nameFilter: String): List<VehicleEntity>

    @Query("SELECT * FROM VEHICLE_LIST_TABLE WHERE id = :id")
    suspend fun getVehicleById(id: Int): VehicleEntity

    @Query("SELECT * FROM VEHICLE_LIST_TABLE WHERE name = :name")
    suspend fun getVehicleByName(name: String): VehicleEntity

    @Query("DELETE FROM VEHICLE_LIST_TABLE")
    suspend fun clearAllVehicles()
}