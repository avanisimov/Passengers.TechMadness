package com.passengers.anroidapp.feature.special_details

import com.passengers.anroidapp.core.BaseViewModel
import com.passengers.anroidapp.network.model.FeedItem
import io.reactivex.subjects.BehaviorSubject

class SpecialDetailsViewModel : BaseViewModel() {

    private val feedItemSubject: BehaviorSubject<FeedItem> = BehaviorSubject.create<FeedItem>()

    override fun init() {

    }
}