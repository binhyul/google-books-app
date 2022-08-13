package com.example.google_books.data

import androidx.paging.PagingData
import com.example.google_books.remote.BookItem
import kotlinx.coroutines.flow.Flow

interface AppRepository {

    fun getTotalBooksCount(): Flow<Int>

    suspend fun getListContents(searchText: String): Flow<PagingData<BookItem>>
}