package com.jhuo.fleetioassessment.data.mappers

import com.jhuo.fleetioassessment.data.model.local.comment.CommentEntity
import com.jhuo.fleetioassessment.data.remote.dto.comment.CommentDto

fun CommentDto.toCommentEntity(): CommentEntity {
    val authorName = author.name
    return CommentEntity(
        id = id,
        author = authorName,
        comment =  comment,
        commentableId = commentable_id,
        commentableType = commentable_type,
        createdAt = created_at,
        htmlContent = html_content,
        richContent = rich_content,
        updatedAt = updated_at,
        userId = user_id,
        withMentions = with_mentions
    )
}
