package com.yuridentadanu.bacabukuapp.view.ui

import android.os.Bundle
import android.util.Log
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
import com.yuridentadanu.bacabukuapp.view.adapter.BookByGenreRecyclerViewAdapter
import com.yuridentadanu.bacabukuapp.view.viewmodel.BookByGenreVM
import kotlinx.android.synthetic.main.book_list_by_genre.*
import kotlinx.android.synthetic.main.book_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BookByGenreFragment : Fragment() {
    lateinit var bookRecyclerViewAdapter: BookByGenreRecyclerViewAdapter
    private lateinit var VM: BookByGenreVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.book_list_by_genre, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        VM = ViewModelProvider(this).get(BookByGenreVM::class.java)
        VM.init(
            (activity as MainActivity).viewModel.getAllRepository(),
            (activity as MainActivity)
        )
        initRecyclerView()
        getListBook()

    }

    private fun initRecyclerView() {
        bookRecyclerViewAdapter = BookByGenreRecyclerViewAdapter(
            context!!,
            VM.getObjectBooksByGenre(),
            object : BookByGenreRecyclerViewAdapter.BookListener {
                override fun itemDetail(bookListDetail: BookByGenreResponse.Result) {
                    VM.getUIScope().launch {
                        withContext(Dispatchers.Default) {
                            (activity as MainActivity).viewModel.setBookId(bookListDetail.id)
                            (activity as MainActivity).addFragment(
                                R.id.frameLayout_activity_main_1,
                                BookDetailFragment(),
                                VM.getUIScope()
                            )
                        }
                    }
                }
            })
        val mLayoutManager = LinearLayoutManager(context)

        rv_bookbyGenre.layoutManager = mLayoutManager
        rv_bookbyGenre.itemAnimator = DefaultItemAnimator()
        rv_bookbyGenre.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        rv_bookbyGenre.adapter = bookRecyclerViewAdapter

    }

    private fun getListBook() {
        val id = (activity as MainActivity).viewModel.getGenreListDetail().id
        Log.d(tag, "getdetailID:$id ")
        VM.getBookByGenreList(id.toString())
        VM.listBook.observe(viewLifecycleOwner, Observer { state ->
            if (state is Success) {
                floatingRefresh2.gone()
                shimmerBookByGenre.stopShimmerAnimation()
                shimmerBookByGenre.gone()
                val response = state.response as BookByGenreResponse
                response.result.let { bookDetail ->
                    VM.initBookByGenreList(bookDetail)
                }
                bookRecyclerViewAdapter.notifyDataSetChanged()
            }

            if (state is Failed) {
                Toast.makeText(context, "Bad Connection", Toast.LENGTH_SHORT).show()
                floatingRefresh2.visible()
                floatingRefresh2.setOnClickListener(){
                    getListBook()
                }
            }
        })
    }

}