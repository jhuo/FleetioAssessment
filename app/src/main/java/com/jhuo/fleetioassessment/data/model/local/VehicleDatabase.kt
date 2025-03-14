package com.jhuo.fleetioassessment.data.model.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jhuo.fleetioassessment.data.model.local.comment.CommentDao
import com.jhuo.fleetioassessment.data.model.local.comment.CommentEntity
import com.jhuo.fleetioassessment.data.model.local.remotekeys.RemoteKeys
import com.jhuo.fleetioassessment.data.model.local.remotekeys.RemoteKeysDao
import com.jhuo.fleetioassessment.data.model.local.vehicle.VehicleDao
import com.jhuo.fleetioassessment.data.model.local.vehicle.VehicleEntity

@Database(
    entities = [VehicleEntity::class, RemoteKeys::class, CommentEntity::class],
    version = 1
)
abstract class VehicleDatabase: RoomDatabase() {
    abstract val vehicleDao: VehicleDao
    abstract val remoteKeysDao: RemoteKeysDao
    abstract val commentDao: CommentDao
}