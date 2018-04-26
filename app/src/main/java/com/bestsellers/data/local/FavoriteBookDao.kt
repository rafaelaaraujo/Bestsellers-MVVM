package com.bestsellers.data.local

import android.arch.persistence.room.*
import com.bestsellers.data.entity.Book

/**
 * Created by rafaela.araujo on 01/03/18.
 */
@Dao
interface FavoriteBookDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBook(book: Book) : Long

    @Delete
    fun deleteBook(book: Book)

    @Query("SELECT * FROM Book")
    fun loadAllFavoriteBooks(): List<Book>

    @Query("SELECT * FROM Book WHERE title = :arg0")
    fun getFavoriteBook(title: String?):Book?

    @Query("DELETE FROM Book")
    fun removeAll()
}