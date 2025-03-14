package com.jhuo.fleetioassessment.presentation.comment_listings

import com.jhuo.fleetioassessment.domain.model.Comment

data class CommentListState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val commentsList: List<Comment> = emptyList()
)
