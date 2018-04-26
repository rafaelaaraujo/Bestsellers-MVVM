package com.bestsellers.bookGenre

import android.widget.Filter
import com.bestsellers.data.entity.Genre

/**
 * Created by Rafaela Araujo
 * on 14/11/2017.
 */
class BookGenresFilter(private val genreList:List<Genre>, private val listener: (List<Genre>) -> Unit) : Filter() {

    override fun performFiltering(sequence: CharSequence): FilterResults {
        val filterResults = FilterResults()
        filterResults.count = genreList.size
        filterResults.values = genreList

        if (sequence.isNotEmpty()) {
            val tempList = ArrayList<Genre>()

            genreList.forEach {
                if (it.display_name.contains(sequence, true)) {
                    tempList.add(it)
                }
            }

            filterResults.count = tempList.size
            filterResults.values = tempList
        }

        return filterResults
    }

    override fun publishResults(query: CharSequence, results: FilterResults) {
        listener(results.values as List<Genre>)
    }

}