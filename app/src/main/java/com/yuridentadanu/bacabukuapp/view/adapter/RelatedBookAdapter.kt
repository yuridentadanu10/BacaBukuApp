package com.yuridentadanu.bacabukuapp.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yuridentadanu.bacabukuapp.R
import com.yuridentadanu.bacabukuapp.model.BookDetailResponse
import kotlinx.android.synthetic.main.item_related_book.view.*

class RelatedBookAdapter : RecyclerView.Adapter<RelatedBookAdapter.UserViewHolder>() {

    private var user: MutableList<BookDetailResponse.Result.RelatedByBook> = mutableListOf()

    internal fun addBook(u: List<BookDetailResponse.Result.RelatedByBook>) {
        notifyDataSetChanged()
        user.clear()
        user.addAll(u)
    }

    inner class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(user: BookDetailResponse.Result.RelatedByBook) {
            with(itemView) {
                val url =
                    "https://cabaca.id:8443/api/v2/files/${user.cover_url}&api_key=32ded42cfffb77dee86a29f43d36a3641849d4b5904aade9a79e9aa6cd5b5948"
                Glide.with(this.context).load(url).into(image_cover)
                tv_title.text = user.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_related_book, parent, false)
        return UserViewHolder(view)
    }

    override fun getItemCount(): Int {
        return user.size
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(user[position])
    }
}