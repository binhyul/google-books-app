package com.example.google_books.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.google_books.local.model.BookEntity


@Database(
    entities = [
        BookEntity::class
    ],
    version = 1
)
abstract class BookDataBase : RoomDatabase() {

    abstract fun shops(): BookDao
}