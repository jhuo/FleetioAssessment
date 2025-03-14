package com.jhuo.fleetioassessment.domain.model


data class Comment(
    val id: Int,
    val authorName: String,
    val comment: String,
    val commentableId: Int,
    val commentableType: String,
    val createdAt: String,
    val htmlContent: String,
    val updatedAt: String,
    val userId: Int,
    val withMentions: Boolean
)
