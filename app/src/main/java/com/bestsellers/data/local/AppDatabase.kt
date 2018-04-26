package com.bestsellers.data.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.bestsellers.BestSellersApplication
import com.bestsellers.data.entity.Book

/**
 * Created by rafaela.araujo on 01/03/18.
 */

@Database(entities = arrayOf(Book::class), version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getFavoriteBookDao(): FavoriteBookDao

    companion object {
        private var appDatabase: AppDatabase? = null
        private val DB_NAME = "book.db"

        fun getInstance(): AppDatabase? {
            if (appDatabase == null) {
                BestSellersApplication.context?.let {
                    appDatabase = Room.databaseBuilder(it,
                            AppDatabase::class.java, DB_NAME)
                            .allowMainThreadQueries()
                            .build()
                }

            }
            return appDatabase
        }
    }
}