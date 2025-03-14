package com.jhuo.fleetioassessment.data.model.local.comment

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jhuo.fleetioassessment.data.remote.dto.comment.Author
import com.jhuo.fleetioassessment.data.remote.dto.comment.RichContent
import com.jhuo.fleetioassessment.util.Constants.COMMENT_LIST_TABLE
import com.jhuo.fleetioassessment.util.Constants.VEHICLE_LIST_TABLE

@Entity(tableName = COMMENT_LIST_TABLE)
data class CommentEntity(
    @PrimaryKey
    val id: Int,
    val author: String,
    val comment: String,
    val commentableId: Int,
    val commentableType: String,
    val createdAt: String,
    val htmlContent: String,
    val richContent: RichContent,
    val updatedAt: String,
    val userId: Int,
    val withMentions: Boolean
)
