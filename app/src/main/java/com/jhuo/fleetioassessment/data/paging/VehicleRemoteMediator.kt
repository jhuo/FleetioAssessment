package com.jhuo.fleetioassessment.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.jhuo.fleetioassessment.data.mappers.toVehicleEntity
import com.jhuo.fleetioassessment.data.model.local.VehicleDatabase
import com.jhuo.fleetioassessment.data.model.local.remotekeys.RemoteKeys
import com.jhuo.fleetioassessment.data.model.local.vehicle.VehicleEntity
import com.jhuo.fleetioassessment.data.remote.VehicleApiService
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class VehicleRemoteMediator(
    private val vehicleApi: VehicleApiService,
    private val vehicleDatabase: VehicleDatabase,
    private val nameFilter: String? = null
) : RemoteMediator<Int, VehicleEntity>() {

    private val vehicleDao = vehicleDatabase.vehicleDao
    private val remoteKeysDao = vehicleDatabase.remoteKeysDao

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, VehicleEntity>
    ): MediatorResult {
        return try {
            val nextCursor = when (loadType) {
                LoadType.REFRESH -> null // Fetch first page
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> remoteKeysDao.getNextCursor() // Get nextCursor from Room
            }

            val vehicleListDto = vehicleApi.getVehicleList(
                startCursor = nextCursor?.nextCursor,
                perPage = state.config.pageSize,
                nameFilter = nameFilter
            )
            val vehicleList = vehicleListDto.vehicles.map { it.toVehicleEntity() }

            vehicleDatabase.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    vehicleDao.clearAllVehicles()
                    remoteKeysDao.clearRemoteKeys()
                }

                vehicleDao.upsertVehicleList(vehicleList)

                // Store nextCursor in Room
                remoteKeysDao.upsertRemoteKey(RemoteKeys(nextCursor = vehicleListDto.nextCursor))
            }

            MediatorResult.Success(endOfPaginationReached = vehicleListDto.nextCursor == null)
        } catch(e: IOException) {
            MediatorResult.Error(e)
        } catch(e: HttpException) {
            MediatorResult.Error(e)
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
