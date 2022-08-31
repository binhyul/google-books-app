package com.example.google_books.data

import androidx.paging.PagingData
import com.example.google_books.local.model.BookEntity
import com.example.google_books.ui.book.model.BookModel
import kotlinx.coroutines.flow.Flow

interface AppRepository {
    suspend fun getListContents(searchText: String): Flow<PagingData<BookModel>>

    suspend fun findBook(id: String): BookEntity?

    suspend fun likeBook(book: BookEntity)

    suspend fun unLikeBook(id: String)
}