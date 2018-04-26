package com.bestsellers.bookGenre

import android.arch.lifecycle.*
import com.bestsellers.data.BestSellersRepository
import com.bestsellers.data.entity.Genre


/**
 * Created by Rafaela
 * on 03/11/2017.
 */
class BookGenreViewModel(private val repository: BestSellersRepository) : ViewModel(), LifecycleObserver {

    private var genresList = MutableLiveData<List<Genre>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun start() {
        requestList()
    }

    fun requestList() {
        repository.getBestSellersGenre({
            genresList.postValue(it.results)
        }, {
            genresList.postValue(null)
        })
    }

    fun getGenresList() = genresList

}