package com.passengers.anroidapp.network.repo.news

import com.passengers.anroidapp.network.model.FeedItem
import io.reactivex.Single

interface NewsRepository {

    fun getNews(): Single<List<FeedItem>>
}