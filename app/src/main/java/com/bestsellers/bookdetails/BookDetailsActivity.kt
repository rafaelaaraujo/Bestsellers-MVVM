package com.bestsellers.bookdetails

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.bestsellers.R
import com.bestsellers.common.BaseActivity
import com.bestsellers.data.entity.Book
import com.bestsellers.util.*
import com.bestsellers.util.ext.*
import kotlinx.android.synthetic.main.details_activity.*
import org.koin.android.architecture.ext.viewModel


class BookDetailsActivity : BaseActivity() {

    private val viewModel by viewModel<BookDetailsViewModel>()
    private lateinit var menuFavorite: MenuItem
    private val menuItemPosition = 0
    private lateinit var book: Book

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.details_activity)
        book = intent.extras.getSerializable(BOOK) as Book
        setBookInformation()

        super.onCreate(savedInstanceState)
    }

    private fun setBookInformation() {
        book.apply {
            configureActionBar(title)
            txtIsbn10.text = getString(R.string.isbn10, getIsbn())
            weeksOnList.text = getWeeksOnTheList(this@BookDetailsActivity)
            titleBook.text = title
            writer.text = contributor
            desc.text = description
            txtPublisher.text = getString(R.string.publisher, publisher)
            rankPosition.text = rank.toString()
            image.loadUrl(book_image)
            shopButton.setOnClickListener { openUrlInBrowser(amazon_product_url) }
        }

        viewModel.getBookAverage(book.getIsbn()).observe(this, Observer {
            reviewsRatingBar.rating = it?.average_rating ?: 0f
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_details, menu)
        menuFavorite = menu.getItem(menuItemPosition)
        changeFavoriteButton(book.favorite)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
            R.id.favorite -> favoriteItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun favoriteItem() {
        changeFavoriteButton(!book.favorite)
        showFavoriteAlert(!book.favorite)
        viewModel.changeBookStatus(book)
    }

    private fun changeFavoriteButton(favorite: Boolean) {
        menuFavorite.apply {
            title = if(favorite){
                setIcon(R.drawable.ic_favorite_selected)
                getString(R.string.not_favorite)
            } else{
                setIcon(R.drawable.ic_favorite_unselected)
                getString(R.string.favorite)
            }
        }
    }

    private fun showFavoriteAlert(favorite: Boolean) {
        if (favorite) {
            showToast(getString(R.string.favorite_message))
        } else {
            showToast(getString(R.string.remove_favorite_message))
        }
    }

}
