package com.bestsellers.bestSellers

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import com.bestsellers.R
import com.bestsellers.bookdetails.BookDetailsActivity
import com.bestsellers.common.BaseActivity
import com.bestsellers.data.entity.Book
import com.bestsellers.util.*
import com.bestsellers.util.ext.*
import com.yarolegovich.discretescrollview.DiscreteScrollView
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.activity_best_sellers.*
import kotlinx.android.synthetic.main.book_card_options.*
import org.koin.android.architecture.ext.viewModel

/**
 * Created by Rafaela
 * on 03/11/2017.
 */
class BestSellersActivity : BaseActivity(), DiscreteScrollView.OnItemChangedListener<BestSellersAdapter.ViewHolder> {

    private val viewModel by viewModel<BestSellersViewModel>{ mapOf("genreName" to genreName) }
    private var booksList = ArrayList<Book>()
    private val genreName by argument<String>(GENRE_NAME)
    private val maxScale = 1.05f
    private val minScale = 0.8f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_best_sellers)
        viewModel.let { lifecycle.addObserver(it) }
        configureView(intent.extras.getString(DISPLAY_NAME))
    }

    private fun configureView(listName: String) {
        configureActionBar(listName)
        configureBestSellersList()
        loadBooks()

        datailsButton.setOnClickListener { showBookDetails() }
        fabbuyButton.setOnClickListener { openUrlInBrowser(getCurrentBook().amazon_product_url) }
        favoriteButton.setOnClickListener { favoriteBook() }
    }

    private fun loadBooks() {
        viewModel.getBestSellerList().observe(this, Observer {
            hideLoading()
            if (it != null) {
                showBestSellers(it)
            } else {
                showErrorMessage()
            }
        })
    }

    private fun favoriteBook() {
        favoriteButton.startBounceAnimation()
        viewModel.changeBookStatus(bestSellersScrollView.currentItem, favoriteButton.isChecked)
        showFavoriteAlert(favoriteButton.isChecked)
    }

    private fun configureBestSellersList() {
        showLoading()
        bestSellersScrollView.setItemTransformer(ScaleTransformer.Builder()
                .setMaxScale(maxScale)
                .setMinScale(minScale)
                .build())

        bestSellersScrollView.adapter = BestSellersAdapter(booksList, this::showBookDetails)
        bestSellersScrollView.addOnItemChangedListener(this)
    }


    private fun showBookDetails() {
        launchActivity<BookDetailsActivity>(bestSellersScrollView.rootView.findViewById(R.id.bookImage)) {
            putExtra(BOOK, getCurrentBook())
        }
    }

    private fun showErrorMessage() {
        hideLoading()
        errorMessage.visibility = VISIBLE
    }

    private fun showLoading() {
        progressBestSellers.visibility = VISIBLE
    }

    private fun hideLoading() {
        progressBestSellers.visibility = GONE
    }

    private fun showBestSellers(bestSeller: List<Book>) {
        booksList.addAll(bestSeller)
        bestSellersScrollView.adapter.notifyDataSetChanged()
        cardOptions.visibility = VISIBLE
    }

    private fun getCurrentBook() = booksList[bestSellersScrollView.currentItem]

    override fun onCurrentItemChanged(viewHolder: BestSellersAdapter.ViewHolder?, position: Int) {
        if (position != -1)
            favoriteButton.isChecked = getCurrentBook().favorite
    }

    private fun showFavoriteAlert(favorite: Boolean) {
        showToast(if (favorite) getString(R.string.favorite_message) else getString(R.string.remove_favorite_message))
    }

}