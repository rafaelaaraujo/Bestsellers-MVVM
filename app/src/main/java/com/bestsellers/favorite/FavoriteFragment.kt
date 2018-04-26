package com.bestsellers.favorite

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import com.bestsellers.R
import com.bestsellers.bookdetails.BookDetailsActivity
import com.bestsellers.data.entity.Book
import com.bestsellers.util.BOOK
import com.bestsellers.util.ext.launchActivity
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.empty_state_view.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by rafaela.araujo on 27/02/18.
 */
class FavoriteFragment : Fragment() {

    private var favoriteList: List<Book> = ArrayList()
    private val viewModel by viewModel<FavoriteViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        viewModel.let { lifecycle.addObserver(it) }
        return inflater.inflate(R.layout.activity_favorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadRecyclerView()
        showLoading()
        loadBooks()
    }

    private fun loadBooks() {
        viewModel.getFavoriteBooks().observe(this, Observer {
            hideLoading()
            if(it != null)
                showFavoriteBooks(it)
            else
                showErrorMessage()
        })
    }

    private fun loadRecyclerView() {
        recyclerFavorite.layoutManager = GridLayoutManager(context, 2)
        recyclerFavorite.adapter = FavoriteAdapter(favoriteList, this::showBookDetails)
        empityStateLayout.visibility = GONE
        recyclerFavorite.visibility = VISIBLE
    }

    private fun showErrorMessage() {
        hideLoading()
        recyclerFavorite.visibility = GONE
        empityStateLayout.visibility = VISIBLE
        txt_message_emptyState.text = getString(R.string.error_loading_data)
    }

    private fun showLoading() {
        progressFavorite.visibility = VISIBLE
    }

    private fun hideLoading() {
        progressFavorite.visibility = GONE
    }

    private fun showFavoriteBooks(list: List<Book>) {
        favoriteList = list
        loadRecyclerView()
    }

    private fun showBookDetails(book: Book) {
        activity?.launchActivity<BookDetailsActivity> { putExtra(BOOK, book) }
    }
}