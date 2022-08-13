package com.example.google_books.remote.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.google_books.data.source.ListContentRemotePagingSource
import com.example.google_books.remote.AppService
import com.example.google_books.remote.BookItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppRemoteDataSourceImpl @Inject constructor(
    private val appService: AppService
) : AppRemoteDataSource {

    override suspend fun getListContents(searchText: String): Flow<PagingData<BookItem>> {
        return Pager(
            PagingConfig(
                ListContentRemotePagingSource.PAGE_SIZE,
                ListContentRemotePagingSource.PAGE_SIZE / 2,
                true,
                ListContentRemotePagingSource.PAGE_SIZE,
            ), 0
        ) { ListContentRemotePagingSource(appService, searchText) }.flow
    }
}