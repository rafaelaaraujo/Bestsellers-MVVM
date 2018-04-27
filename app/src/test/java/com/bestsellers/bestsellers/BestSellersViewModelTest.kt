package com.bestsellers.bestsellers

import android.arch.lifecycle.Observer
import com.bestsellers.BaseTest
import com.bestsellers.bestSellers.BestSellersViewModel
import com.bestsellers.data.entity.BestSellers
import com.bestsellers.data.entity.Book
import com.bestsellers.data.entity.Results
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Created by Rafaela Araujo
 * on 15/03/2018.
 */
class BestSellersViewModelTest : BaseTest() {


    @Mock
    private lateinit var observer: Observer<List<Book>>
    private lateinit var viewModel: BestSellersViewModel

    @Before
    fun setup() {
        initMocks()
        viewModel = BestSellersViewModel("test", repository)
    }

    @Test
    fun getBestSellersList() {
        Mockito.`when`(service.getBestSeller(anyString())).thenReturn(Observable.just(getBestSellersMock()))

        viewModel.getBestSellerList().observeForever(observer)
        viewModel.requestBestSellers()

        assert(viewModel.getBestSellerList().value == listOf(getBestSellersMock()))
    }

    private fun getBestSellersMock(): BestSellers {
        return BestSellers(Results(listOf(getEmptyBook())))
    }

    private fun getEmptyBook() = Book("tests", 1, 3, "", "", "", "", "", ArrayList())

}