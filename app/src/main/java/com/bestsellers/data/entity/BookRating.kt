package com.bestsellers.data.entity

/**
 * Created by rafaela.araujo on 26/02/18.
 */

data class BookRating(
        val books: List<Average>
)

data class Average (
        val id: String,
        val average_rating: Float)