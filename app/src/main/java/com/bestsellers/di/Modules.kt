package com.bestsellers.di

import com.bestsellers.bestSellers.BestSellersViewModel
import com.bestsellers.bookGenre.BookGenreViewModel
import com.bestsellers.bookdetails.BookDetailsViewModel
import com.bestsellers.data.BestSellersRepository
import com.bestsellers.favorite.FavoriteViewModel
import org.koin.android.architecture.ext.viewModel
import org.koin.dsl.module.applicationContext

/**
 * Koin main module
 */
val module = applicationContext {

    viewModel { BookGenreViewModel(get()) }
    viewModel { param -> BestSellersViewModel(param["genreName"], get()) }
    viewModel { BookDetailsViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }

    bean { BestSellersRepository() }
}

/**
 * module list
 */
val appModules = listOf(module)
