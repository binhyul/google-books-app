package com.example.google_books.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.example.google_books.data.AppRepository
import com.example.google_books.remote.toEntity
import com.example.google_books.ui.book.BookModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class LoadBooksUseCase @Inject constructor(
    private val appRepository: AppRepository
) : FlowUseCase<String, PagingData<BookModel>>() {
    override suspend fun execute(parameters: String): Flow<PagingData<BookModel>> {
        return appRepository.getListContents(parameters).map { pagingData ->
            pagingData.map {
                it.toEntity()
            }
        }
    }
}


