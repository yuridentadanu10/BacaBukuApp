package com.yuridentadanu.bacabukuapp.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yuridentadanu.bacabukuapp.R
import com.yuridentadanu.bacabukuapp.model.GenreResponse
import de.hdodenhof.circleimageview.CircleImageView


class GenreRecyclerViewAdapter(
    internal var context: Context
    , private var genreObject: List<GenreObject>
    , private var bookListener: BookListener
) : RecyclerView.Adapter<GenreRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView_cover: CircleImageView = view.findViewById(R.id.image_genre)
        var tv_genre: TextView = view.findViewById(R.id.tv_genre)
        var tv_count: TextView = view.findViewById(R.id.tv_count)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemLayout = R.layout.item_genre
        val itemView = LayoutInflater.from(parent.context)
            .inflate(itemLayout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val genreList = genreObject[position].genreListDetail


        when (genreList.title) {
            "Romance" -> holder.imageView_cover.setImageResource(R.drawable.ic_romance)
            "Adult" -> holder.imageView_cover.setImageResource(R.drawable.ic_adult)
            "Comedy" -> holder.imageView_cover.setImageResource(R.drawable.ic_comedy)
            "Action" -> holder.imageView_cover.setImageResource(R.drawable.ic_action)
            else -> holder.imageView_cover.setImageResource(R.drawable.ic_placeholder)
        }

        holder.tv_genre.text = genreList.title
        holder.itemView.setOnClickListener { bookListener.itemDetail(genreList) }
        holder.tv_count.text = "Total: "+ genreList.count.toString()


    }

    override fun getItemCount(): Int {
        var itemCount = 0

        try {
            val itemSize = genreObject.size
            itemCount = itemSize
        } catch (e: Exception) {
        }

        return itemCount
    }

    interface BookListener {
        fun itemDetail(genreListDetail: GenreResponse.Resource)
    }

    class GenreObject(var genreListDetail: GenreResponse.Resource)

}