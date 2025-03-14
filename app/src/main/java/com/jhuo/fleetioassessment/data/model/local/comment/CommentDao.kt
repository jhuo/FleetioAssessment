package com.jhuo.fleetioassessment.data.model.local.comment

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CommentDao {
    @Upsert
    suspend fun upsertVehicleList(vehicleList: List<CommentEntity>)

    @Upsert
    suspend fun upsertVehicle(vehicle: CommentEntity)

    @Query("SELECT * FROM VEHICLE_LIST_TABLE ORDER BY id DESC")
    fun getVehiclesPagingSource(): PagingSource<Int, CommentEntity>

    @Query("SELECT * FROM VEHICLE_LIST_TABLE WHERE LOWER(status) LIKE LOWER(:nameFilter) OR UPPER(:nameFilter) = status")
    suspend fun getVehicleList(nameFilter: String): List<CommentEntity>

    @Query("SELECT * FROM VEHICLE_LIST_TABLE WHERE id = :id")
    suspend fun getVehicleById(id: Int): CommentEntity

    @Query("SELECT * FROM VEHICLE_LIST_TABLE WHERE name = :name")
    suspend fun getVehicleByName(name: String): CommentEntity

    @Query("DELETE FROM VEHICLE_LIST_TABLE")
    suspend fun clearAllVehicles()
}