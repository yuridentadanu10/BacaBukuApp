package com.yuridentadanu.bacabukuapp.model

data class WriterDetailResponse(
    val result: Result,
    val success: Boolean
) {
    data class Result(
        val birthday: String,
        val coin: Int,
        val count_follower: Int,
        val count_following: Int,
        val current_level: Int,
        val deskripsi: String,
        val email: String,
        val following_user: List<Any>,
        val gender: String,
        val id: Int,
        val is_following: Boolean,
        val karya: List<Any>,
        val link_user: String,
        val name: String,
        val phone: String,
        val photo_url: String,
        val reading_list: List<Any>,
        val username: String
    )
}