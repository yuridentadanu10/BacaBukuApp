package com.yuridentadanu.bacabukuapp.view.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.yuridentadanu.bacabukuapp.R
import com.yuridentadanu.bacabukuapp.model.*
import com.yuridentadanu.bacabukuapp.view.adapter.GenreRecyclerViewAdapter
import com.yuridentadanu.bacabukuapp.view.viewmodel.GenreVM
import kotlinx.android.synthetic.main.book_list_fragment.*
import kotlinx.android.synthetic.main.genre_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GenreListFragment: Fragment() {
    lateinit var bookRecyclerViewAdapter: GenreRecyclerViewAdapter
    private lateinit var VM: GenreVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.genre_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        VM = ViewModelProvider(this).get(GenreVM::class.java)
        VM.init(
            (activity as MainActivity).viewModel.getAllRepository(),
            (activity as MainActivity)
        )
        initRecyclerView()
        getListBook()

    }

    private fun initRecyclerView() {
        bookRecyclerViewAdapter = GenreRecyclerViewAdapter(
            context!!,
            VM.getObjectBooks(),
            object : GenreRecyclerViewAdapter.BookListener {
                override fun itemDetail(genreListDetail: GenreResponse.Resource) {
                    VM.getUIScope().launch {
                        withContext(Dispatchers.Default) {
                            (activity as MainActivity).viewModel.setGenreListDetail(genreListDetail)
                            (activity as MainActivity).addFragment(
                                R.id.frameLayout_activity_main_1,
                                BookByGenreFragment(),
                                VM.getUIScope()
                            )
                        }
                    }
                }
            })
        val mLayoutManager = LinearLayoutManager(context)

        rv_genreList.layoutManager = mLayoutManager
        rv_genreList.itemAnimator = DefaultItemAnimator()
        rv_genreList.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        rv_genreList.adapter = bookRecyclerViewAdapter

    }

    private fun getListBook(){
        VM.getBookList()
        VM.listBook.observe(viewLifecycleOwner, Observer { state ->
            if (state is Success){
                floatingRefresh3.gone()
                shimmerGenreList.stopShimmerAnimation();
                shimmerGenreList.gone()

                val response = state.response as GenreResponse
                response.resource.let {genreDetail ->
                    VM.initBookList(genreDetail)
                }
                bookRecyclerViewAdapter.notifyDataSetChanged()
            }

            if (state is Failed){
                Toast.makeText(context, "Bad Connection", Toast.LENGTH_SHORT).show()
                floatingRefresh3.visible()
                floatingRefresh3.setOnClickListener(){
                    getListBook()
                }
            }
        })


    }

}