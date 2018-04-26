package com.bestsellers.util.ext

import android.content.Context
import com.bestsellers.R
import com.bestsellers.data.entity.Book

/**
 * Created by rafaela.araujo on 06/03/18.
 */

/**
 * get first isbns from book
 */
fun Book.getIsbn(): String = if (isbns.isNotEmpty()) isbns[0].isbn10 else "-"

/**
 * format week number to formated test to display it
 */
fun Book.getWeeksOnTheList(context: Context): String? {
    return if (weeks_on_list <= 1) {
        context.getString(R.string.new_this_week)
    } else {
        context.getString(R.string.weeks_on_list, weeks_on_list)
    }
}