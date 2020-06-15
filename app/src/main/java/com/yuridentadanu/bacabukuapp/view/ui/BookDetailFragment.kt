package com.yuridentadanu.bacabukuapp.view.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.yuridentadanu.bacabukuapp.R
import com.yuridentadanu.bacabukuapp.model.*
import com.yuridentadanu.bacabukuapp.view.adapter.RelatedBookAdapter
import com.yuridentadanu.bacabukuapp.view.viewmodel.BookDetailVM
import kotlinx.android.synthetic.main.detail_book_fragment.*
import kotlinx.android.synthetic.main.item_writer.*
import kotlinx.android.synthetic.main.layout_detail_body.*
import kotlinx.android.synthetic.main.layout_detail_header.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers


class BookDetailFragment : Fragment() {
    private lateinit var relatedBookAdapter: RelatedBookAdapter
    private lateinit var DetailVM: BookDetailVM
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_book_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        DetailVM = ViewModelProvider(this).get(BookDetailVM::class.java)
        DetailVM.init(
            (activity as MainActivity).viewModel.getAllRepository(),
            (activity as MainActivity)
        )
        getdetailID()
    }

    private fun getdetailID() {
        val id = (activity as MainActivity).viewModel.getBookId()
        Log.d(tag, "getdetailID:$id ")
        DetailVM.getBookById(id.toString())
        DetailVM.DetailBook.observe(viewLifecycleOwner, Observer { state ->
            Log.d(tag, "getState:$state ")
            if (state is Success) {
                progress_Detail.gone()
                layoutDetail.visible()

                val response = state.response as BookDetailResponse
                response.result.let { bookDetail ->

                    tv_title.text = bookDetail.title
                    val imageUrl =
                        "https://cabaca.id:8443/api/v2/files/" + bookDetail.cover_url + "&api_key=$API_KEY"
                    Glide.with(this).load(imageUrl).into(image_coverBook)

                    val writer = bookDetail.Writer_by_writer_id.User_by_user_id
                    val imageWriter =
                        "https://cabaca.id:8443/api/v2/files/" + writer.photo_url + "&api_key=$API_KEY"

                    Glide.with(this).load(imageWriter).into(image_genre)

                    tv_genre.text = writer.name
                    tv_count.text = writer.username

                    btn_readBook.setOnClickListener {
                        val browserIntent =
                            Intent(Intent.ACTION_VIEW, Uri.parse(bookDetail.url_landing))
                        startActivity(browserIntent)
                    }

                    tv_click_detail_writer.setOnClickListener {
                        (activity as MainActivity).viewModel.setBookId(writer.id)
                        (activity as MainActivity).addFragment(
                            R.id.frameLayout_activity_main_1,
                            WriterDetailFragment(),
                            CoroutineScope(Dispatchers.Main)

                        )
                    }

                    tv_sinopsis.text = bookDetail.desc
                    rating_book.rating = bookDetail.average_rate.toFloat()
                    relatedBookAdapter = RelatedBookAdapter()
                    rv_related_book.layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rv_related_book.adapter = relatedBookAdapter
                    relatedBookAdapter.addBook(bookDetail.Related_by_book)


                }
            }
            if (state is Failed) {
                Toast.makeText(context, "Bad Connection", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
