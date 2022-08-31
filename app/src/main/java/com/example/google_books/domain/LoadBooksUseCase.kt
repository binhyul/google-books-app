package com.example.google_books.domain

import androidx.paging.PagingData
import com.example.google_books.data.AppRepository
import com.example.google_books.ui.book.model.BookModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LoadBooksUseCase @Inject constructor(
    private val appRepository: AppRepository
) : FlowUseCase<String, PagingData<BookModel>>() {
    override suspend fun execute(parameters: String): Flow<PagingData<BookModel>> {
        return appRepository.getListContents(parameters)
    }
}


