package com.passengers.anroidapp.feature.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.passengers.anroidapp.R
import com.passengers.anroidapp.core.BaseFragment

class ChatFragment : BaseFragment() {

    lateinit var chatViewModel : ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        chatViewModel = ViewModelProviders.of(activity!!)[ChatViewModel::class.java]
        chatViewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_chat, container, false)
    }

}