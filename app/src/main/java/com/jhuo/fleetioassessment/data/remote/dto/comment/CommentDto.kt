package com.jhuo.fleetioassessment.data.remote.dto.comment

data class CommentDto(
    val author: Author,
    val comment: String,
    val commentable_id: Int,
    val commentable_type: String,
    val created_at: String,
    val html_content: String,
    val id: Int,
    val rich_content: RichContent,
    val updated_at: String,
    val user_id: Int,
    val with_mentions: Boolean
)