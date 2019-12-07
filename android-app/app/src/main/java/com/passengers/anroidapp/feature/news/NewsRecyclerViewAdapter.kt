package com.passengers.anroidapp.feature.news

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.passengers.anroidapp.R
import com.passengers.anroidapp.feature.news.NewsRecyclerViewAdapter.NewsViewHolder
import com.passengers.anroidapp.network.model.FeedItem
import com.passengers.anroidapp.network.model.FeedItemType
import java.util.*

class NewsRecyclerViewAdapter : RecyclerView.Adapter<NewsViewHolder>() {

    private var data: List<FeedItem> = ArrayList()

    fun setData(data: List<FeedItem>) {
        this.data = data
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.item_feed, parent, false))
    }

    @SuppressLint("DefaultLocale")
    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val feedItem = data[position]

        holder.titleView.text = feedItem.title
        holder.datetimeView.text = String.format("%d %s %d",
                feedItem.date?.dayOfMonth,
                feedItem.date?.month,
                feedItem.date?.year)
        holder.specialView.visibility =
                if (feedItem.type == FeedItemType.SPECIAL_DEAL) {
                    View.VISIBLE
                } else {
                    View.GONE
                }

        Glide
                .with(holder.itemView)
                .load(feedItem.imageUrl)
                .centerCrop()
                .into(holder.photoView)

    }

    override fun getItemCount(): Int {
        return data.size
    }

    class NewsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var titleView: TextView = view.findViewById(R.id.title)
        var datetimeView: TextView = view.findViewById(R.id.datetime)
        var specialView: TextView = view.findViewById(R.id.special)
        var photoView: ImageView = view.findViewById(R.id.photo)

    }
}