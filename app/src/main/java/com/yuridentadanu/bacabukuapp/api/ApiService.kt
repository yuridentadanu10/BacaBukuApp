package com.yuridentadanu.bacabukuapp.api

import com.yuridentadanu.bacabukuapp.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("api/v2/cabaca/_table/genre")
    suspend fun getAllGenre(@Header(HEADER) header: String)
            : GenreResponse

    @GET("api/v2/book/category?")
    suspend fun getBooksByGenre(
        @Header(HEADER) header: String,
        @Query("id") genreId: String
    )
            : BookByGenreResponse


    @GET("api/v2/book/uptodate?limit=7")
    suspend fun getBookList(@Header(HEADER) header: String)
            : BookListResponse


    @GET("api/v2/book/detail/{book_id}")
    suspend fun getBookDetail(
        @Header(HEADER) header: String,
        @Path("book_id") bookId: String
    )
            : BookDetailResponse

    @GET("api/v2/writer/detail/{user_id}")
    suspend fun getWriterDetail(
        @Header(HEADER) header: String,
        @Path("user_id") writerId: String
    ): WriterDetailResponse

    companion object Factory {
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://cabaca.id:8443/")
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}