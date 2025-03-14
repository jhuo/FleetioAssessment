package com.jhuo.fleetioassessment.data.repository

import android.R.attr.category
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jhuo.fleetioassessment.data.mappers.toComment
import com.jhuo.fleetioassessment.data.mappers.toCommentEntity
import com.jhuo.fleetioassessment.data.mappers.toVehicle
import com.jhuo.fleetioassessment.data.mappers.toVehicleEntity
import com.jhuo.fleetioassessment.data.model.local.VehicleDatabase
import com.jhuo.fleetioassessment.data.remote.VehicleApiService
import com.jhuo.fleetioassessment.domain.model.Vehicle
import com.jhuo.fleetioassessment.data.model.local.vehicle.VehicleEntity
import com.jhuo.fleetioassessment.data.paging.VehicleRemoteMediator
import com.jhuo.fleetioassessment.domain.model.Comment
import com.jhuo.fleetioassessment.domain.repository.VehicleRepository
import com.jhuo.fleetioassessment.util.Constants.PAGE_SIZE
import com.jhuo.fleetioassessment.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class VehicleRepositoryImpl @Inject constructor(
    private val vehicleApiService: VehicleApiService,
    private val vehicleDatabase: VehicleDatabase
): VehicleRepository {

    private val vehicleDao = vehicleDatabase.vehicleDao
    private val commentDao = vehicleDatabase.commentDao

    override suspend fun getVehicleOverview(id: Int): Flow<Resource<Vehicle>> {
        return flow {
            emit(Resource.Loading(true))
            val vehicleEntity = vehicleDao.getVehicleById(id)
            emit(Resource.Success(vehicleEntity.toVehicle()))
            emit(Resource.Loading(false))
        }
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun getVehiclesPaged(nameFilter: String?): Flow<PagingData<VehicleEntity>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            remoteMediator = VehicleRemoteMediator(vehicleApiService, vehicleDatabase, nameFilter),
            pagingSourceFactory = { vehicleDao.getVehiclesPagingSource() }
        ).flow
    }

    override suspend fun updateVehicleDetails(vehicleId: Int, updateRequest: Map<String, String>): Resource<Vehicle> {
        return try {
            val response = vehicleApiService.updateVehicleDetails(vehicleId, updateRequest)

            if (response.isSuccessful && response.body() != null) {
                val updatedVehicle = response.body()!!.toVehicleEntity()
                vehicleDao.upsertVehicle(updatedVehicle)
                Resource.Success(updatedVehicle.toVehicle())
            } else {
                Resource.Error("Failed to update mileage: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "An unexpected error occurred")
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getCommentsListByVehicleId(id: Int, forceFetchFromRemote: Boolean): Flow<Resource<List<Comment>>> {
        return flow {
            emit(Resource.Loading(true))
            val localCommentsList = commentDao.getCommentByVehicleId(id)
            val shouldLoadLocalComments = localCommentsList.isNotEmpty() && !forceFetchFromRemote
            if (shouldLoadLocalComments) {
                emit(Resource.Success(
                    data = localCommentsList.map { commentEntity ->
                        commentEntity.toComment()
                    }
                ))

                emit(Resource.Loading(false))
                return@flow
            }

            val commentsListFromApi = try {
                vehicleApiService.getComments(id)
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading comments"))
                return@flow
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading comments"))
                return@flow
            } catch (e: Exception) {
                e.printStackTrace()
                emit(Resource.Error(message = "Error loading comments"))
                return@flow
            }

            val commentEntities = commentsListFromApi.commentsList.let {
                it.map { commentDto ->
                    commentDto.toCommentEntity()
                }
            }

            commentDao.upsertCommentList(commentEntities)

            emit(Resource.Success(
                commentEntities.map { it.toComment() }
            ))
            emit(Resource.Loading(false))
        }
    }
}