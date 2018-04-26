package com.bestsellers.bookdetails

import android.arch.lifecycle.Observer
import com.bestsellers.BaseTest
import com.bestsellers.data.entity.Average
import com.bestsellers.data.entity.BookRating
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
class BookDetailsViewModelTest : BaseTest(){

    @Mock
    private lateinit var observer: Observer<Average>
    private lateinit var viewModel: BookDetailsViewModel

    @Before
    fun setup() {
        initMocks()
        viewModel = BookDetailsViewModel(repository)
    }

    @Test
    fun getBookAverage_updateView() {

        Mockito.`when`(service.getBookAverage(anyString())).thenReturn(Observable.just(getBookaverage()))

        viewModel.getAverage().observeForever(observer)
        viewModel.getBookAverage(anyString())

        assert(viewModel.getAverage().value == getAverage())
    }


    private fun getBookaverage(): BookRating {
        return BookRating(listOf(getAverage()))
    }

    private fun getAverage() = Average("10", 5f)
}