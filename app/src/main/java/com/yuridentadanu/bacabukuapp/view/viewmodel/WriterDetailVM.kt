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


class WriterDetailVM : ViewModel() {
    private lateinit var allRepository: AllRepository
    private lateinit var mainActivity: MainActivity
    private var job: Job? = null


    fun init(allRepository: AllRepository, mainActivity: MainActivity) {
        this.allRepository = allRepository
        this.mainActivity = mainActivity
    }

    val DetailWriter: LiveData<State> get() = mDetailWriter
    private val mDetailWriter = MutableLiveData<State>()

    fun getWriterById(WriterId: String) {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            allRepository.getWriterDetail(WriterId).collect {
                mDetailWriter.postValue(it)
                Log.d("ITTTTTTT", "getAllGenre:$it ")
            }
        }
    }
}