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
import com.yuridentadanu.bacabukuapp.view.adapter.BookRecyclerViewAdapter
import com.yuridentadanu.bacabukuapp.view.viewmodel.BookListVM
import kotlinx.android.synthetic.main.book_list_fragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class BookListFragment : Fragment() {
    lateinit var bookRecyclerViewAdapter: BookRecyclerViewAdapter
    private lateinit var VM: BookListVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.book_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        VM = ViewModelProvider(this).get(BookListVM::class.java)
        VM.init(
            (activity as MainActivity).viewModel.getAllRepository(),
            (activity as MainActivity)
        )
        initRecyclerView()
        getListBook()

    }

    private fun initRecyclerView() {
        bookRecyclerViewAdapter = BookRecyclerViewAdapter(
            context!!,
            VM.getObjectBooks(),
            object : BookRecyclerViewAdapter.BookListener {
                override fun itemDetail(bookListDetail: BookListResponse.Result) {
                    VM.getUIScope().launch {
                        withContext(Dispatchers.Default) {
                            (activity as MainActivity).viewModel.setBookId(bookListDetail.book_id)
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

        rv_listBook.layoutManager = mLayoutManager
        rv_listBook.itemAnimator = DefaultItemAnimator()
        rv_listBook.addItemDecoration(
            DividerItemDecoration(
                activity,
                LinearLayoutManager.VERTICAL
            )
        )
        rv_listBook.adapter = bookRecyclerViewAdapter

    }

    private fun getListBook() {
        VM.getBookList()
        VM.listBook.observe(viewLifecycleOwner, Observer { state ->
            if (state is Success) {
                floatingRefresh.gone()
                shimmerBookList.stopShimmerAnimation()
                shimmerBookList.gone()

                val response = state.response as BookListResponse
                response.result.let { bookDetail ->
                    VM.initBookList(bookDetail)
                }
                bookRecyclerViewAdapter.notifyDataSetChanged()
            }

            if (state is Failed) {
                Toast.makeText(context, "Bad Connection", Toast.LENGTH_SHORT).show()
                floatingRefresh.visible()
                floatingRefresh.setOnClickListener(){
                    getListBook()
                }
            }
        })


    }

}
