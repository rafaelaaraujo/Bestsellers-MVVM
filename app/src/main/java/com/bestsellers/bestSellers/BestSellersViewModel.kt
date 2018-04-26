package com.bestsellers.bestSellers

import android.arch.lifecycle.*
import com.bestsellers.data.BestSellersRepository
import com.bestsellers.data.entity.Book

/**
 * Created by Rafaela Araujo
 * on 03/11/2017.
 */
class BestSellersViewModel(private val genreName : String,
                           private val repository: BestSellersRepository)
    : ViewModel(), LifecycleObserver {

    private var liveBestSellerList = MutableLiveData<List<Book>>()

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun loadBooks() {
        if (liveBestSellerList.value == null)
            requestBestSellers()
        else
            checkFavoriteBookStatus(liveBestSellerList.value)
    }

    fun requestBestSellers() {
        repository.getBestSellers(genreName, {
            checkFavoriteBookStatus(it.results.books)
        }, {
            liveBestSellerList.postValue(null)
        })
    }

    fun changeBookStatus(bookPosition: Int, isFavorite: Boolean) {
        liveBestSellerList.value?.get(bookPosition)?.let {
            if (isFavorite) {
                it.favorite = true
                repository.favoriteBook(it)
            } else {
                repository.removeFavoriteBook(it)
            }

            it.favorite = isFavorite
        }
    }

    private fun checkFavoriteBookStatus(list: List<Book>?) {
        list?.forEach {
                val favoriteBook = repository.getBookFavorite(it.title)
                it.favorite = favoriteBook != null
            }
            liveBestSellerList.postValue(list)
    }

    fun getBestSellerList() = liveBestSellerList

}