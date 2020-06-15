package com.yuridentadanu.bacabukuapp.model

data class BookByGenreResponse(
    val result: List<Result>,
    val success: Boolean,
    val test: Test
) {
    data class Result(
        val Genre_by_genre_id: GenreByGenreId,
        val Writer_by_writer_id: WriterByWriterId,
        val book_url: String,
        val chapter_count: Int,
        val cover_url: String,
        val created_at: String,
        val genre_id: Int,
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
        data class GenreByGenreId(
            val count: Int,
            val icon_url: String,
            val id: Int,
            val title: String
        )

        data class WriterByWriterId(
            val User_by_user_id: UserByUserId,
            val created_at: String,
            val id: Int,
            val kelas: Any,
            val royalty_id: Int,
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

    data class Test(
        val id: Int
    )
}