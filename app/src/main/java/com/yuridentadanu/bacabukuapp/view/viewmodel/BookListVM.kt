package com.yuridentadanu.bacabukuapp.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuridentadanu.bacabukuapp.model.BookListResponse
import com.yuridentadanu.bacabukuapp.model.State
import com.yuridentadanu.bacabukuapp.repository.AllRepository
import com.yuridentadanu.bacabukuapp.view.adapter.BookRecyclerViewAdapter
import com.yuridentadanu.bacabukuapp.view.ui.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class BookListVM : ViewModel() {

    private lateinit var allRepository: AllRepository
    private lateinit var mainActivity: MainActivity
    private val uiScope = CoroutineScope(Dispatchers.Main)
    private val bookObjects: MutableList<BookRecyclerViewAdapter.BookObject> = ArrayList()
    private lateinit var bookObject: BookRecyclerViewAdapter.BookObject

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
            allRepository.getBookList().collect {
                mListBook.postValue(it)
                Log.d("ITTTTTTT", "getAllGenre:$it ")
            }
        }
    }

    fun getUIScope(): CoroutineScope {
        return uiScope
    }

    fun getObjectBooks(): MutableList<BookRecyclerViewAdapter.BookObject> {
        return bookObjects
    }

    fun initBookList(bookList: List<BookListResponse.Result>) {
        bookObjects.clear()
        for (i in bookList.indices) {
            bookObject = BookRecyclerViewAdapter.BookObject(bookList[i])
            bookObjects.add(bookObject)

        }

    }

}
