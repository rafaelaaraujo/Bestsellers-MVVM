package com.bestsellers.data

import com.bestsellers.data.entity.BestSellers
import com.bestsellers.data.entity.Book
import com.bestsellers.data.entity.BookGenres
import com.bestsellers.data.entity.BookRating
import com.bestsellers.data.local.AppDatabase
import com.bestsellers.data.local.FavoriteBookDao
import com.bestsellers.data.remote.BestSellersService
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Rafaela Araujo
 * on 05/03/2018.
 */
open class BestSellersRepository(
        private val service: BestSellersService = BestSellersService(),
        private var favoriteDao: FavoriteBookDao? = null,
        private val ioScheduler: Scheduler = Schedulers.io(),
        private val mainScheduler: Scheduler = AndroidSchedulers.mainThread()) {

    init {
        if(favoriteDao == null)
            favoriteDao = getFavoriteDao()
    }

    fun getBookAverage(isbn: String, success: (BookRating) -> Unit, error: () -> Unit) {
        doRequest(service.getBookAverage(isbn), success, error)
    }

    fun getBestSellers(name: String, success: (BestSellers) -> Unit, error: () -> Unit) {
        doRequest(service.getBestSeller(name), success, error)
    }

    fun getBestSellersGenre(success: (BookGenres) -> Unit, error: () -> Unit) {
        doRequest(service.getGenreList(), success, error)
    }

    fun getFavoriteBooks(): List<Book>? {
        return favoriteDao?.loadAllFavoriteBooks()
    }

    fun removeFavoriteBook(book: Book) {
        favoriteDao?.deleteBook(book)
    }

    fun favoriteBook(book: Book) {
        favoriteDao?.insertBook(book)
    }

    fun getBookFavorite(title: String?): Book? {
        return favoriteDao?.getFavoriteBook(title)
    }

    private fun getFavoriteDao() = AppDatabase.getInstance()?.getFavoriteBookDao()


    private fun <T> doRequest(observable: Observable<T>, success: (T) -> Unit, error: () -> Unit) {
        observable
                .subscribeOn(ioScheduler)
                .observeOn(mainScheduler)
                .subscribe({ success(it) }, { error() })
    }

}