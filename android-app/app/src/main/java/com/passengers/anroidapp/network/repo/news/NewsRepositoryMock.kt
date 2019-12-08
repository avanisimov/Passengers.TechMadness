package com.passengers.anroidapp.network.repo.news

import com.passengers.anroidapp.network.model.FeedItem
import com.passengers.anroidapp.network.repo.MockCollection
import io.reactivex.Single

class NewsRepositoryMock : NewsRepository {


    override fun getNews(): Single<List<FeedItem>> =
        Single.fromCallable { MockCollection.feedItems }


}
