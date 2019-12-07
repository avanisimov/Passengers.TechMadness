package com.passengers.anroidapp.feature.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.passengers.anroidapp.R
import com.passengers.anroidapp.core.BaseFragment

class NewsFragment : BaseFragment() {

    lateinit var newsViewModel : NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        newsViewModel = ViewModelProviders.of(activity!!)[NewsViewModel::class.java]
        newsViewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

}