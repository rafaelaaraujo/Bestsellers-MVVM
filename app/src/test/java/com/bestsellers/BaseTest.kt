package com.bestsellers

import com.bestsellers.data.BestSellersRepository

import com.bestsellers.data.local.FavoriteBookDao
import com.bestsellers.data.remote.BestSellersService
import io.reactivex.schedulers.Schedulers
import org.mockito.Mock
import org.mockito.MockitoAnnotations

open class BaseTest  {

    lateinit var repository: BestSellersRepository
    @Mock lateinit var service: BestSellersService
    @Mock lateinit var favoriteDao: FavoriteBookDao


    fun initMocks() {
        MockitoAnnotations.initMocks(this)
        repository = BestSellersRepository(service, favoriteDao, Schedulers.trampoline(), Schedulers.trampoline())
    }
}