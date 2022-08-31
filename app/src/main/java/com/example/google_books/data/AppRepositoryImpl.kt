package com.example.google_books.data

import androidx.paging.PagingData
import androidx.paging.map
import com.example.google_books.local.model.BookEntity
import com.example.google_books.local.model.toModel
import com.example.google_books.local.source.AppLocalDataSource
import com.example.google_books.remote.model.BookItem
import com.example.google_books.remote.model.toModel
import com.example.google_books.remote.source.AppRemoteDataSource
import com.example.google_books.ui.book.model.BookModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppRepositoryImpl @Inject constructor(
    private val remoteDataSource: AppRemoteDataSource,
    private val localDataSource: AppLocalDataSource
) : AppRepository {
    override suspend fun getListContents(searchText: String): Flow<PagingData<BookModel>> {
        return remoteDataSource.getListContents(searchText).map { pageData ->
            pageData.map {
                it.findLikeBook()
            }
        }
    }

    private suspend fun BookItem.findLikeBook(): BookModel {
        val findLikeBook = findBook(id)?.toModel()
        return findLikeBook ?: toModel(false)
    }

    override suspend fun findBook(id: String): BookEntity? = localDataSource.findBook(id)

    override suspend fun likeBook(book: BookEntity) = localDataSource.likeBook(book)

    override suspend fun unLikeBook(id: String) = localDataSource.unLikeBook(id)


}