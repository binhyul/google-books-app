package com.example.google_books.local.source

import com.example.google_books.local.BookDao
import com.example.google_books.local.model.BookEntity
import javax.inject.Inject

class AppLocalDataSourceImpl @Inject constructor(
    private val shopDao: BookDao
) : AppLocalDataSource {

    override suspend fun findBook(id: String): BookEntity? = shopDao.getBook(id)

    override suspend fun likeBook(book: BookEntity) = shopDao.insertBook(book)

    override suspend fun unLikeBook(id: String) = shopDao.deleteBook(id)
}