package com.yuridentadanu.bacabukuapp.model

data class BookListResponse( val result: List<Result>,
                             val success: Boolean,
                             val time: Time
) {
    data class Result(
        val Writer_by_writer_id: WriterByWriterId,
        val book_id: Int,
        val category: Any,
        val chapter_count: Int,
        val cover_url: String,
        val created_at: String,
        val id: Int,
        val isNew: Boolean,
        val is_update: Boolean,
        val rate_sum: Double,
        val schedule_task: String,
        val status: String,
        val title: String,
        val view_count: Int,
        val writer_id: Int
    ) {
        data class WriterByWriterId(
            val User_by_user_id: UserByUserId,
            val created_at: String,
            val id: Int,
            val kelas: String,
            val royalty_id: Any,
            val schedule_task: String,
            val status: Any,
            val type: String,
            val user_id: Int
        ) {
            data class UserByUserId(
                val id: Int,
                val name: String
            )
        }
    }

    data class Time(
        val book_official: Double,
        val chapter: Double,
        val chapter_book: Double,
        val chapter_new: Double,
        val rating: Double,
        val user: Double,
        val viewer: Double
    )
}