package com.yuridentadanu.bacabukuapp.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yuridentadanu.bacabukuapp.api.ApiService
import com.yuridentadanu.bacabukuapp.model.GenreResponse
import com.yuridentadanu.bacabukuapp.repository.AllRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

class MainActivityVM : ViewModel() {
    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)
    private var allRepository: AllRepository? = null
    private val toolbarTitle = MutableLiveData<String>()
    private lateinit var genreListDetail: GenreResponse.Resource
    private var bookId: Int = 0

    fun init() {
        if (allRepository == null) {
            allRepository = AllRepository(ApiService.create())

        }
        Log.d("TESNANU123", "init:$uiScope ")
    }

    fun getJob(): Job {
        return job
    }

    fun getUIScope(): CoroutineScope {
        return uiScope
    }

    fun getAllRepository(): AllRepository {
        return allRepository!!
    }

    fun setToolbarTitle(toolbarTitle: String) {
        this.toolbarTitle.value = toolbarTitle
    }

    fun getToolbarTitle(): LiveData<String> {
        return toolbarTitle
    }

    fun setGenreListDetail(genreListDetail: GenreResponse.Resource) {
        this.genreListDetail = genreListDetail
    }

    fun getGenreListDetail(): GenreResponse.Resource {
        return genreListDetail
    }

    fun setBookId(bookId: Int) {
        this.bookId = bookId
    }

    fun getBookId(): Int {
        return bookId
    }

}