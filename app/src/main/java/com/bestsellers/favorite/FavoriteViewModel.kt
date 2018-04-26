package com.bestsellers.favorite

import android.arch.lifecycle.*
import com.bestsellers.data.BestSellersRepository
import com.bestsellers.data.entity.Book

/**
 * Created by rafaela.araujo on 27/02/18.
 */
class FavoriteViewModel (private val repository: BestSellersRepository): ViewModel(), LifecycleObserver {

    private val favoriteBooks = MutableLiveData<List<Book>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun start(){
        loadFavoriteBooks()
    }

    fun loadFavoriteBooks() {
        val list = repository.getFavoriteBooks()
        if (list != null && list.isNotEmpty()) {
            favoriteBooks.postValue(list)
        } else {
            favoriteBooks.postValue(null)
        }
    }

    fun getFavoriteBooks() = favoriteBooks
}