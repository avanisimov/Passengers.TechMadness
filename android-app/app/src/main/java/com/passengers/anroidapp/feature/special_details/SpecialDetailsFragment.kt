package com.passengers.anroidapp.feature.special_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.passengers.anroidapp.R
import com.passengers.anroidapp.core.BaseFragment
import com.passengers.anroidapp.feature.news.NewsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class SpecialDetailsFragment : BaseFragment() {

    val specialsViewModel : SpecialDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        specialsViewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_specials, container, false)
    }

}