package com.yuridentadanu.bacabukuapp.model

data class GenreResponse(
    val resource: List<Resource>
) {
    data class Resource(
        val count: Int,
        val icon_url: String,
        val id: Int,
        val title: String
    )
}