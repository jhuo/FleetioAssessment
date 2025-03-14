package com.jhuo.fleetioassessment.data.remote.dto.comment

import com.google.gson.annotations.SerializedName

data class CommentsListDto(
    val estimated_remaining_count: Int,
    val filtered_by: List<FilteredBy>,
    val next_cursor: Any,
    val per_page: Int,
    @SerializedName("records")val commentsList: List<CommentDto>,
    val sorted_by: List<SortedBy>,
    val start_cursor: String
)