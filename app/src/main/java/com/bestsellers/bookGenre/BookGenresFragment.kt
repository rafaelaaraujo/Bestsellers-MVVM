package com.bestsellers.bookGenre

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView.AdapterDataObserver
import android.support.v7.widget.SearchView
import android.view.*
import android.view.View.GONE
import android.view.View.VISIBLE
import com.bestsellers.R
import com.bestsellers.bestSellers.BestSellersActivity
import com.bestsellers.data.entity.Genre
import com.bestsellers.util.DISPLAY_NAME
import com.bestsellers.util.GENRE_NAME
import com.bestsellers.util.ext.launchActivity
import kotlinx.android.synthetic.main.activity_best_sellers.*
import kotlinx.android.synthetic.main.activity_genre.*
import org.koin.android.architecture.ext.viewModel


/**
 * Created by rafaela.araujo
 * on 07/11/17.
 */

class BookGenresFragment : Fragment(),SearchView.OnQueryTextListener {

    private val viewModel by viewModel<BookGenreViewModel>()
    private lateinit var adapter: BookGenresAdapter
    private var genreList = ArrayList<Genre>()
    private var searchView: SearchView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        viewModel.let { lifecycle.addObserver(it) }
        return inflater.inflate(R.layout.activity_genre, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loadData()
        initUI()
    }

    private fun loadData() {
        showLoading()

        viewModel.getGenresList().observe(this, Observer {
            hideLoading()
            if (it != null) {
                showGenreList(it)
            } else {
                showErrorMessage()
            }
        })
    }

    private fun initUI() {
        genreGrid.layoutManager = LinearLayoutManager(activity)
        adapter = BookGenresAdapter(genreList, this::openListByGenre)
        adapter.registerAdapterDataObserver(observer)
        genreGrid.adapter = adapter
    }

    private val observer: AdapterDataObserver = object : AdapterDataObserver() {
        override fun onChanged() {
            super.onChanged()
            checkAdapterIsEmpty()
        }
    }

    private fun checkAdapterIsEmpty() {
        genreNotFoundMessge.visibility = if (adapter.itemCount == 0) VISIBLE else GONE
    }

    private fun openListByGenre(genre: Genre) {
        activity?.launchActivity<BestSellersActivity> {
            putExtra(GENRE_NAME, genre.list_name)
            putExtra(DISPLAY_NAME, genre.display_name)
        }
    }

    private fun showErrorMessage() {
        hideLoading()
        errorMessage.visibility = VISIBLE
    }

    private fun showLoading() {
        progressGenre.visibility = VISIBLE
    }

    private fun hideLoading() {
        progressGenre.visibility = GONE
    }

    private fun showGenreList(genreList: List<Genre>) {
        this.genreList.addAll(genreList)
        adapter.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
        configureSearchView(menu)
    }

    private fun configureSearchView(menu: Menu) {
        searchView = menu.findItem(R.id.search).actionView as? SearchView
        searchView?.setOnQueryTextListener(this)
        searchView?.clearFocus()
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        searchView?.clearFocus()
        return submitQuery(query)
    }

    override fun onQueryTextChange(query: String): Boolean {
        return submitQuery(query)
    }

    private fun submitQuery(query: String): Boolean {
        adapter.filter.filter(query)
        return true
    }
}