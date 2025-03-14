package com.jhuo.fleetioassessment.data.remote.dto.vehicle

import com.google.gson.annotations.SerializedName

data class VehicleListDto(
    val estimated_remaining_count: Int,
    val filtered_by: List<Any>,
    @SerializedName("next_cursor") val nextCursor: String?,
    val per_page: Int,
    @SerializedName("records") val vehicles: List<VehicleDto>,
    val sorted_by: List<SortedBy>,
    val start_cursor: String
)
