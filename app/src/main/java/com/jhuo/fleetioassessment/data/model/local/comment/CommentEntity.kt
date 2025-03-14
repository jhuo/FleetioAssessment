package com.jhuo.fleetioassessment.data.model.local.comment

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jhuo.fleetioassessment.util.Constants.COMMENT_LIST_TABLE

@Entity(tableName = COMMENT_LIST_TABLE)
data class CommentEntity(
    @PrimaryKey
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
