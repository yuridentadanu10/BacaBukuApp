package com.yuridentadanu.bacabukuapp.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuridentadanu.bacabukuapp.model.GenreResponse
import com.yuridentadanu.bacabukuapp.model.State
import com.yuridentadanu.bacabukuapp.repository.AllRepository
import com.yuridentadanu.bacabukuapp.view.adapter.GenreRecyclerViewAdapter
import com.yuridentadanu.bacabukuapp.view.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class GenreVM : ViewModel() {

    private lateinit var allRepository: AllRepository
    private lateinit var mainActivity: MainActivity
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val genreObjects: MutableList<GenreRecyclerViewAdapter.GenreObject> = ArrayList()
    private lateinit var genreObject: GenreRecyclerViewAdapter.GenreObject

    fun init(allRepository: AllRepository, mainActivity: MainActivity) {
        this.allRepository = allRepository
        this.mainActivity = mainActivity

    }

    val listBook: LiveData<State> get() = mListBook
    private val mListBook = MutableLiveData<State>()
    private var getBookListJob: Job? = null

    fun getBookList() {
        Log.d("halohalo", "getBookById: ")
        getBookListJob?.cancel()
        getBookListJob = viewModelScope.launch(Dispatchers.IO) {
            allRepository.getGenre().collect {
                mListBook.postValue(it)
                Log.d("ITTTTTTT", "getAllGenre:$it ")
            }
        }
    }

    fun getUIScope(): CoroutineScope {
        return uiScope
    }

    fun getObjectBooks(): MutableList<GenreRecyclerViewAdapter.GenreObject> {
        return genreObjects
    }

    fun initBookList(genreList: List<GenreResponse.Resource>) {
        genreObjects.clear()
        for (i in genreList.indices) {
            genreObject = GenreRecyclerViewAdapter.GenreObject(genreList[i])
            genreObjects.add(genreObject)

        }

    }
}