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
import com.bumptech.glide.Glide
import com.yuridentadanu.bacabukuapp.R
import com.yuridentadanu.bacabukuapp.model.*
import com.yuridentadanu.bacabukuapp.view.viewmodel.WriterDetailVM
import kotlinx.android.synthetic.main.detail_writer_fragment.*

class WriterDetailFragment : Fragment() {
    private lateinit var DetailVM: WriterDetailVM

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detail_writer_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        DetailVM = ViewModelProvider(this).get(WriterDetailVM::class.java)
        DetailVM.init(
            (activity as MainActivity).viewModel.getAllRepository(),
            (activity as MainActivity)
        )
        getdetailID()
    }

    private fun getdetailID() {
        val id = (activity as MainActivity).viewModel.getBookId()
        Log.d(tag, "getdetailID:$id ")
        DetailVM.getWriterById(id.toString())
        DetailVM.DetailWriter.observe(viewLifecycleOwner, Observer { state ->
            Log.d(tag, "getState:$state ")
            if (state is Success) {
                progress_Detail2.gone()
                layoutDetail2.visible()
                val response = state.response as WriterDetailResponse
                response.result.let { writerDetail ->
                    tv_nama.text = writerDetail.name
                    tv_email.text = writerDetail.email
                    tv_noHP.text = writerDetail.phone
                    tv_tanggalLahir.text = writerDetail.birthday
                    tv_Deskripsi.text = writerDetail.deskripsi
                    val imageUrl =
                        "https://cabaca.id:8443/api/v2/files/" + writerDetail.photo_url + "&api_key=$API_KEY_IMAGE"
                    Glide.with(this).load(imageUrl).into(image_writer)
                }
            }
            if (state is Failed) {
                Toast.makeText(context, "Bad Connection", Toast.LENGTH_SHORT).show()
            }
        })
    }
}