package com.jhuo.fleetioassessment.data.model.local.remotekeys

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RemoteKeysDao {
    @Query("SELECT * FROM VEHICLE_REMOTE_KEYS_TABLE WHERE id = 1")
    suspend fun getNextCursor(): RemoteKeys?

    @Upsert()
    suspend fun upsertRemoteKey(remoteKey: RemoteKeys)

    @Query("DELETE FROM VEHICLE_REMOTE_KEYS_TABLE")
    suspend fun clearRemoteKeys()
}