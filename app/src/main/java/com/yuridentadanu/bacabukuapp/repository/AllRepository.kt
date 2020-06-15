package com.yuridentadanu.bacabukuapp.repository

import android.util.Log
import com.yuridentadanu.bacabukuapp.api.ApiService
import com.yuridentadanu.bacabukuapp.model.API_KEY
import com.yuridentadanu.bacabukuapp.model.Failed
import com.yuridentadanu.bacabukuapp.model.Loading
import com.yuridentadanu.bacabukuapp.model.Success
import kotlinx.coroutines.flow.flow

class AllRepository(private val apiService: ApiService) {

    fun getBookDetail(bookId: String) = flow {
        emit(Loading)
        try {
            val result = apiService.getBookDetail(API_KEY, bookId)
            Log.d("Repository", "result:$result ")
            emit(Success(result))
        } catch (e: Exception) {

            emit(Failed(e))
        }
    }

    fun getWriterDetail(bookId: String) = flow {
        emit(Loading)
        try {
            val result = apiService.getWriterDetail(API_KEY, bookId)
            Log.d("Repository", "result:$result ")
            emit(Success(result))
        } catch (e: Exception) {

            emit(Failed(e))
        }
    }

    fun getBooksByGenre(genreId: String) = flow {
        emit(Loading)
        try {
            val result = apiService.getBooksByGenre(API_KEY, genreId)
            Log.d("Repository", "result:$result ")
            emit(Success(result))
        } catch (e: Exception) {

            emit(Failed(e))
        }
    }


    fun getBookList() = flow {
        emit(Loading)
        try {
            val result = apiService.getBookList(API_KEY)
            Log.d("Repository", "result:$result ")
            emit(Success(result))
        } catch (e: Exception) {
            emit(Failed(e))
        }
    }

    fun getGenre() = flow {
        emit(Loading)
        try {
            val result = apiService.getAllGenre(API_KEY)
            Log.d("Repository", "result:$result ")
            emit(Success(result))
        } catch (e: Exception) {
            emit(Failed(e))
        }
    }
}