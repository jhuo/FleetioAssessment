package com.jhuo.fleetioassessment.data.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.jhuo.fleetioassessment.data.model.local.comment.CommentEntity
import com.jhuo.fleetioassessment.data.remote.dto.comment.CommentDto
import com.jhuo.fleetioassessment.domain.model.Comment
import com.jhuo.fleetioassessment.util.DateTimeUtils

fun CommentDto.toCommentEntity(): CommentEntity {
    val authorName = author.name
    return CommentEntity(
        id = id,
        authorName = authorName,
        comment = comment,
        commentableId = commentable_id,
        commentableType = commentable_type,
        createdAt = created_at,
        htmlContent = html_content,
        updatedAt = updated_at,
        userId = user_id,
        withMentions = with_mentions
    )
}

@RequiresApi(Build.VERSION_CODES.O)
fun CommentEntity.toComment(): Comment {


    return Comment(
        id = id,
        authorName = authorName,
        comment = comment,
        commentableId = commentableId,
        commentableType = commentableType,
        createdAt = DateTimeUtils.formatDateTime(DateTimeUtils.parseDateTime(createdAt)),
        htmlContent = htmlContent,
        updatedAt = updatedAt,
        userId = userId,
        withMentions = withMentions
    )
}
