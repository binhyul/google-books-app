package com.example.google_books.data

import androidx.paging.PagingData
import com.example.google_books.remote.BookItem
import com.example.google_books.remote.source.AppRemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val dataSource: AppRemoteDataSource
) : AppRepository {

    override suspend fun getListContents(searchText: String): Flow<PagingData<BookItem>> =
        dataSource.getListContents(searchText)
}