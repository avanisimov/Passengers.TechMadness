package com.passengers.anroidapp.feature.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.passengers.anroidapp.R
import com.passengers.anroidapp.core.BaseFragment
import com.passengers.anroidapp.core.SimpleDisposable
import com.passengers.anroidapp.network.model.FeedItem
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment : BaseFragment() {

    val newsViewModel: NewsViewModel by viewModel()

    lateinit var recyclerView: RecyclerView
    lateinit var adapter: NewsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsViewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_news, container, false)

        recyclerView = view.findViewById(R.id.feed_items)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = NewsRecyclerViewAdapter()
        recyclerView.adapter = adapter

    }

    override fun onResume() {
        super.onResume()

        addDisposables(
                newsViewModel
                        .getFeedItems()
                        .subscribeWith(object : SimpleDisposable<List<FeedItem>>() {
                            override fun onNext(t: List<FeedItem>) {
                                super.onNext(t)
                                adapter.setData(t)
                                adapter.notifyDataSetChanged()
                            }
                        })
        )
    }

}