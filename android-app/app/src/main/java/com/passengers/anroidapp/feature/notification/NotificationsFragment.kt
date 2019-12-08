package com.passengers.anroidapp.feature.notification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.passengers.anroidapp.R
import com.passengers.anroidapp.core.BaseFragment

class NotificationsFragment : BaseFragment() {

    lateinit var notificationsViewModel: NotificationsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        notificationsViewModel = ViewModelProviders.of(activity!!)[NotificationsViewModel::class.java]
        notificationsViewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

}