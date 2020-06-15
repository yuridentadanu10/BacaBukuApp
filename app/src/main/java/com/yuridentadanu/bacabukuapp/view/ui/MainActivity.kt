package com.yuridentadanu.bacabukuapp.view.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yuridentadanu.bacabukuapp.R
import com.yuridentadanu.bacabukuapp.view.viewmodel.MainActivityVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var viewModel: MainActivityVM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainActivityVM::class.java)
        viewModel.getUIScope().launch(Dispatchers.Default) {
            viewModel.init()
            replaceFragment(
                R.id.frameLayout_activity_main_1,
                HomeFragment(),
                viewModel.getUIScope()
            )

        }

    }

    override fun onDestroy() {
        viewModel.getJob().cancel()
        super.onDestroy()
    }

    fun replaceFragment(layout: Int, fragment: Fragment, uiScope: CoroutineScope) {
        uiScope.launch {
            supportFragmentManager
                .beginTransaction()
                .replace(layout, fragment)
                .commitAllowingStateLoss()
        }
    }

    fun addFragment(layout: Int, fragment: Fragment, uiScope: CoroutineScope) {
        uiScope.launch {
            supportFragmentManager
                .beginTransaction()
                .addToBackStack(null)
                .add(layout, fragment)
                .commitAllowingStateLoss()


        }

    }
}