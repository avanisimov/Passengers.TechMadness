package com.passengers.anroidapp.feature.special

import com.passengers.anroidapp.core.BaseViewModel
import com.passengers.anroidapp.network.model.FeedItem
import com.passengers.anroidapp.network.repo.news.NewsRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject

class SpecialsViewModel(private val newsRepository: NewsRepository) : BaseViewModel() {

    private val feedItemsSubject: BehaviorSubject<List<FeedItem>> = BehaviorSubject.create<List<FeedItem>>()

    override fun init() {
        addDisposables(
                newsRepository.getNews()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(Consumer<List<FeedItem>> {
                            feedItemsSubject.onNext(it)
                        })
        )
    }

    fun getFeedItems() : Observable<List<FeedItem>> =
            feedItemsSubject.hide()
}