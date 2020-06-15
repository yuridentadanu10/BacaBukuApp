package com.yuridentadanu.bacabukuapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yuridentadanu.bacabukuapp.R
import com.yuridentadanu.bacabukuapp.model.API_KEY_IMAGE
import com.yuridentadanu.bacabukuapp.model.BookByGenreResponse


class BookByGenreRecyclerViewAdapter(
    internal var context: Context
    , private var bookObject: List<BookObject>
    , private var bookListener: BookListener
) : RecyclerView.Adapter<BookByGenreRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView_cover: ImageView = view.findViewById(R.id.image_coverBook)
        var tv_writer_username: TextView = view.findViewById(R.id.tv_count)
        var tvRating: RatingBar = view.findViewById(R.id.rating_book)
        var tv_rating: TextView = view.findViewById(R.id.tv_Rating)
        var tv_writer: TextView = view.findViewById(R.id.tv_genre)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayout = R.layout.item_book
        val itemView = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val mBookListDetail = bookObject[position].bookListDetail

        val imageUrl =
            "https://cabaca.id:8443/api/v2/files/" + mBookListDetail.cover_url + "&api_key=$API_KEY_IMAGE"
        Glide.with(context).load(imageUrl).into(holder.imageView_cover)
        holder.tvRating.rating = mBookListDetail.rate_sum.toFloat()
        holder.tv_rating.text = mBookListDetail.rate_sum.toFloat().toString()
        holder.itemView.setOnClickListener { bookListener.itemDetail(mBookListDetail) }
        holder.tv_writer.text = mBookListDetail.title
        holder.tv_writer_username.text = mBookListDetail.Writer_by_writer_id.User_by_user_id.name


    }

    override fun getItemCount(): Int {
        var itemCount = 0
        try {
            val itemSize = bookObject.size
            itemCount = itemSize
        } catch (e: Exception) {
        }

        return itemCount
    }

    interface BookListener {
        fun itemDetail(bookListDetail: BookByGenreResponse.Result)
    }

    class BookObject(var bookListDetail: BookByGenreResponse.Result)

}