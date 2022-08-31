package com.example.google_books.local.source

import com.example.google_books.local.model.BookEntity


interface AppLocalDataSource {

    suspend fun findBook(id: String): BookEntity?

    suspend fun likeBook(book: BookEntity)

    suspend fun unLikeBook(id: String)
}