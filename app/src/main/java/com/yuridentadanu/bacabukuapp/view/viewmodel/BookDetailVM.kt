package com.yuridentadanu.bacabukuapp.view.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yuridentadanu.bacabukuapp.model.State
import com.yuridentadanu.bacabukuapp.repository.AllRepository
import com.yuridentadanu.bacabukuapp.view.ui.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class BookDetailVM : ViewModel() {

    private lateinit var allRepository: AllRepository
    private lateinit var mainActivity: MainActivity
    private var job: Job? = null


    fun init(allRepository: AllRepository, mainActivity: MainActivity) {
        this.allRepository = allRepository
        this.mainActivity = mainActivity
    }

    val DetailBook: LiveData<State> get() = mDetailBook
    private val mDetailBook = MutableLiveData<State>()

    fun getBookById(bookId: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            allRepository.getBookDetail(bookId).collect {
                mDetailBook.postValue(it)
                Log.d("ITTTTTTT", "getAllGenre:$it ")
            }
        }
    }


}
