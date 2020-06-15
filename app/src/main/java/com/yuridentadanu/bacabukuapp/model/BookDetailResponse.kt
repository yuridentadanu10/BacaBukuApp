package com.yuridentadanu.bacabukuapp.model

data class BookDetailResponse(
    val result: Result,
    val success: Boolean
) {
    data class Result(
        val BookChapter_by_book_id: List<BookChapterByBookId>,
        val Related_by_book: List<RelatedByBook>,
        val User_review: String,
        val Writer_by_writer_id: WriterByWriterId,
        val auto_buy_chapter: Boolean,
        val average_rate: Int,
        val book_url: String,
        val category: Any,
        val challenge_id: Any,
        val chapter_count: Int,
        val chapter_paid_count: Int,
        val cover_url: String,
        val created_at: String,
        val daily: String,
        val decimal_rate: Double,
        val desc: String,
        val download: Int,
        val full_price: Int,
        val full_purchase: Boolean,
        val full_purchased: Boolean,
        val genres: List<Genre>,
        val happyhour: Boolean,
        val hashtags: List<Any>,
        val id: Int,
        val isNew: Boolean,
        val is_contract_actived: Boolean,
        val is_free: Boolean,
        val is_update: Boolean,
        val nominasi: Any,
        val reviews: List<Review>,
        val schedule_daily: Int,
        val schedule_task: String,
        val status: String,
        val synopsis: String,
        val time_to_vote: Boolean,
        val title: String,
        val updated_at: String,
        val url_landing: String,
        val view_count: Int,
        val vote_count: String,
        val voted: Boolean,
        val writer_id: Int
    ) {
        data class BookChapterByBookId(
            val book_id: Int,
            val chapter_sequence: Int,
            val created_at: String,
            val id: Int,
            val is_purchased: Boolean,
            val is_readed: Boolean,
            val is_warning: Any,
            val like_count: Int,
            val new: Boolean,
            val price: Int,
            val schedule_task: String,
            val title: String,
            val view_by_user: Int,
            val view_count: Int
        )

        data class RelatedByBook(
            val cover_url: String,
            val id: Int,
            val title: String
        )

        data class WriterByWriterId(
            val User_by_user_id: UserByUserId,
            val id: Int,
            val status: Any,
            val user_id: Int
        ) {
            data class UserByUserId(
                val id: Int,
                val name: String,
                val photo_url: String,
                val username: String
            )
        }

        data class Genre(
            val count: Int,
            val icon_url: String,
            val id: Int,
            val is_primary: Int,
            val title: String
        )

        data class Review(
            val User_by_reviewer_id: UserByReviewerId,
            val id: Int,
            val rating: Int,
            val review: String,
            val updated_at: String,
            val user_id: Int
        ) {
            data class UserByReviewerId(
                val email: String,
                val id: Int,
                val name: String,
                val photo_url: String,
                val username: String
            )
        }
    }
}