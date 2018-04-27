package com.bestsellers.genre

import android.arch.lifecycle.Observer
import com.bestsellers.BaseTest
import com.bestsellers.bookGenre.BookGenreViewModel
import com.bestsellers.data.entity.BookGenres
import com.bestsellers.data.entity.Genre
import io.reactivex.Observable.just
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*


/**
 * Created by rafaela.araujo on 15/03/18.
 */

class BookGenreViewModelTest: BaseTest() {

    @Mock private lateinit var observer: Observer<List<Genre>>
    private lateinit var viewModel: BookGenreViewModel

    @Before
    fun setup() {
        initMocks()
        viewModel = BookGenreViewModel(repository)
    }

    @Test
    fun getGenreList() {
        `when`(service.getGenreList()).thenReturn(just(getMockGenreList()))

//        viewModel.getGenresList().observeForever(observer)
        viewModel.requestList()

        assert(viewModel.getGenresList().value != null)
        assert(viewModel.getGenresList().value?.get(0)?.list_name.equals("test"))
    }

    @Test
    fun getEmptyGenreListNullValue() {
        `when`(service.getGenreList()).thenReturn(just(BookGenres(listOf())))

        viewModel.getGenresList().observeForever(observer)
        viewModel.requestList()

        assert(viewModel.getGenresList().value == null)

    }

    private fun getMockGenreList(): BookGenres {
        return BookGenres(listOf(Genre("test", "", "")))
    }

}

