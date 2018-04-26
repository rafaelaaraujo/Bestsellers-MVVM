package com.bestsellers.bookdetails

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.bestsellers.data.BestSellersRepository
import com.bestsellers.data.entity.Average
import com.bestsellers.data.entity.Book

/**
 * Created by Rafaela
 * on 03/11/2017.
 */
class BookDetailsViewModel(private val repository: BestSellersRepository) : ViewModel() {

    private var average = MutableLiveData<Average>()

    fun getBookAverage(isbn: String?): MutableLiveData<Average> {
        isbn?.let {
            repository.getBookAverage(isbn, {
                if (it.books.isNotEmpty())
                    average.postValue(it.books[0])
                else
                    average.postValue(null)

            }, {
                average.postValue(null)
            })
        }

        return average
    }

    fun getAverage() = average


    fun changeBookStatus(b: Book) {
        if (!b.favorite) {
            b.favorite = true
            repository.favoriteBook(b)
        } else {
            repository.removeFavoriteBook(b)
        }

    }
}