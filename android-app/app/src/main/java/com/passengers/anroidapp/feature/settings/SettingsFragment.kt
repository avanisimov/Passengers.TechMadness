package com.passengers.anroidapp.feature.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.passengers.anroidapp.R
import com.passengers.anroidapp.core.BaseFragment

class SettingsFragment : BaseFragment() {

    lateinit var settingsViewModel : SettingsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        settingsViewModel = ViewModelProviders.of(activity!!)[SettingsViewModel::class.java]
        settingsViewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

}