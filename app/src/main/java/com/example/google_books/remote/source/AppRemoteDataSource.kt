package com.example.google_books.remote.source

import androidx.paging.PagingData
import com.example.google_books.remote.BookItem
import kotlinx.coroutines.flow.Flow

interface AppRemoteDataSource {

    fun getTotalBooksCount(): Flow<Int>

    suspend fun getListContents(searchText: String): Flow<PagingData<BookItem>>
}