package com.passengers.anroidapp.feature.special

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.passengers.anroidapp.R
import com.passengers.anroidapp.core.BaseFragment

class SpecialsFragment : BaseFragment() {

    lateinit var specialsViewModel : SpecialsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        specialsViewModel = ViewModelProviders.of(activity!!)[SpecialsViewModel::class.java]
        specialsViewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_specials, container, false)
    }

}