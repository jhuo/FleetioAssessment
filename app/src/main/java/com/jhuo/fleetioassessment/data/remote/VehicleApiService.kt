package com.jhuo.fleetioassessment.data.remote

import com.jhuo.fleetioassessment.data.remote.dto.comment.CommentDto
import com.jhuo.fleetioassessment.data.remote.dto.vehicle.VehicleDto
import com.jhuo.fleetioassessment.data.remote.dto.vehicle.VehicleListDto
import com.jhuo.fleetioassessment.util.Constants.ACCOUNT_TOKEN
import com.jhuo.fleetioassessment.util.Constants.AUTH_HEADER
import com.jhuo.fleetioassessment.util.Constants.PAGE_SIZE
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

interface VehicleApiService {
    @GET("v1/vehicles")
    suspend fun getVehicleList(
        @Header("Authorization") authHeader: String = AUTH_HEADER,
        @Header("Account-Token") accountToken: String = ACCOUNT_TOKEN,
        @Query("start_cursor") startCursor: String? = null,
        @Query("per_page") perPage: Int? = PAGE_SIZE,
        @Query("filter[name][like]") nameFilter: String? = null
    ): VehicleListDto

    @GET("v1/vehicles/{id}")
    suspend fun getVehicleDetails(
        @Path("id") vehicleId: Int,
        @Header("Authorization") authHeader: String = AUTH_HEADER,
        @Header("Account-Token") accountToken: String = ACCOUNT_TOKEN
    ): Response<VehicleDto>

    @PATCH("v1/vehicles/{id}")
    suspend fun updateVehicleDetails(
        @Path("id") vehicleId: Int,
        @Body updateRequest: Map<String, String>,
        @Header("Authorization") authHeader: String = AUTH_HEADER,
        @Header("Account-Token") accountToken: String = ACCOUNT_TOKEN
    ): Response<VehicleDto>

    @GET("v1/comments")
    suspend fun getComments(
        @Query("filter[commentable_id][eq]") commentableId: Int,
        @Header("Authorization") authHeader: String = AUTH_HEADER,
        @Header("Account-Token") accountToken: String = ACCOUNT_TOKEN
    ): List<CommentDto>
}
