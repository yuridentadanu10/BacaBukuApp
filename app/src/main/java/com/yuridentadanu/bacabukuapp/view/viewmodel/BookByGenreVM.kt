package com.yuridentadanu.bacabukuapp.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuridentadanu.bacabukuapp.model.BookByGenreResponse
import com.yuridentadanu.bacabukuapp.model.State
import com.yuridentadanu.bacabukuapp.repository.AllRepository
import com.yuridentadanu.bacabukuapp.view.adapter.BookByGenreRecyclerViewAdapter
import com.yuridentadanu.bacabukuapp.view.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookByGenreVM : ViewModel() {
    private lateinit var allRepository: AllRepository
    private lateinit var mainActivity: MainActivity
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bookObjects: MutableList<BookByGenreRecyclerViewAdapter.BookObject> = ArrayList()
    private lateinit var bookObject: BookByGenreRecyclerViewAdapter.BookObject

    fun init(allRepository: AllRepository, mainActivity: MainActivity) {
        this.allRepository = allRepository
        this.mainActivity = mainActivity

    }

    val listBook: LiveData<State> get() = mListBook
    private val mListBook = MutableLiveData<State>()
    private var getBookListJob: Job? = null

    fun getBookByGenreList(genreId: String) {
        Log.d("Booklist", "getBookById: ")
        getBookListJob?.cancel()
        getBookListJob = viewModelScope.launch(Dispatchers.IO) {
            allRepository.getBooksByGenre(genreId).collect {
                mListBook.postValue(it)
                Log.d("ITTTTTTT", "getAllGenre:$it ")
            }
        }
    }

    fun getUIScope(): CoroutineScope {
        return uiScope
    }

    fun getObjectBooksByGenre(): MutableList<BookByGenreRecyclerViewAdapter.BookObject> {
        return bookObjects
    }

    fun initBookByGenreList(bookList: List<BookByGenreResponse.Result>) {
        bookObjects.clear()
        for (i in bookList.indices) {
            bookObject = BookByGenreRecyclerViewAdapter.BookObject(bookList[i])
            bookObjects.add(bookObject)

        }

    }
}