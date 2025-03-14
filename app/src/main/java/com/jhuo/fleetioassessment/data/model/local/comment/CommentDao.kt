package com.jhuo.fleetioassessment.data.model.local.comment

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface CommentDao {
    @Upsert
    suspend fun upsertCommentList(commentsList: List<CommentEntity>)

    @Upsert
    suspend fun upsertComment(comment: CommentEntity)

    @Query("SELECT * FROM COMMENT_LIST_TABLE WHERE id = :id")
    suspend fun getCommentById(id: Int): CommentEntity

    @Query("DELETE FROM COMMENT_LIST_TABLE")
    suspend fun clearAllComments()
}