package com.example.google_books.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.example.google_books.data.AppRepository
import com.example.google_books.local.model.toModel
import com.example.google_books.ui.book.model.BookModel
import javax.inject.Inject

class UpdateBooksUseCase @Inject constructor(
    private val appRepository: AppRepository
) : UseCase<PagingData<BookModel>, PagingData<BookModel>>() {

    override suspend fun execute(parameters: PagingData<BookModel>): PagingData<BookModel> {
        return parameters.map {
            it.findLikeBook()
        }

    }

    private suspend fun BookModel.findLikeBook(): BookModel {
        val findLikeBook = appRepository.findBook(id)?.toModel()
        return findLikeBook ?: this.copy(like = false)
    }
}