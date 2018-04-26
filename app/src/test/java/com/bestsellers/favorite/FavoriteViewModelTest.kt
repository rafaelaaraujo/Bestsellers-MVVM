package com.bestsellers.favorite

import android.arch.lifecycle.Observer
import com.bestsellers.BaseTest
import com.bestsellers.data.entity.Book
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito

/**
 * Created by rafaela.araujo on 22/03/18.
 */
class FavoriteViewModelTest : BaseTest() {

    @Mock private lateinit var observer: Observer<List<Book>>
    private lateinit var viewModel: FavoriteViewModel


    @Before
    fun setup() {
        initMocks()
        viewModel = FavoriteViewModel(repository)
    }

    @Test
    fun getFavoritesBook_updateBooksList(){

        Mockito.`when`(favoriteDao.loadAllFavoriteBooks()).thenReturn(listOf(getEmptyBook()))

        viewModel.getFavoriteBooks().observeForever(observer)
        viewModel.loadFavoriteBooks()

        assert(viewModel.getFavoriteBooks().value == listOf(getEmptyBook()))
    }

    private fun getEmptyBook() = Book("test", 1, 3, "", "", "", "", "", ArrayList())

}