package com.example.google_books.remote.source

import androidx.paging.PagingData
import com.example.google_books.remote.model.BookItem
import kotlinx.coroutines.flow.Flow

interface AppRemoteDataSource {

    suspend fun getListContents(searchText: String): Flow<PagingData<BookItem>>
}