package com.jhuo.fleetioassessment.data.model.local.remotekeys

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jhuo.fleetioassessment.util.Constants.VEHICLE_REMOTE_KEYS_TABLE

@Entity(tableName = VEHICLE_REMOTE_KEYS_TABLE)
data class RemoteKeys(
    @PrimaryKey
    val id: Int = 1,
    val nextCursor: String?
)
