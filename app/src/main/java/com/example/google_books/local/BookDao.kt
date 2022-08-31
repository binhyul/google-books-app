package com.example.google_books.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.google_books.local.model.BookEntity


@Dao
interface BookDao {

    @Insert
    suspend fun insertBook(shop: BookEntity)

    @Query("SELECT * FROM book WHERE id = :id")
    suspend fun getBook(id: String): BookEntity?

    @Query("DELETE FROM book WHERE id = :id")
    suspend fun deleteBook(id: String)

    @Query("DELETE FROM book")
    suspend fun clear()

}