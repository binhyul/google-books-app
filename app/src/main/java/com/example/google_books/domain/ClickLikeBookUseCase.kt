package com.example.google_books.domain

import com.example.google_books.data.AppRepository
import com.example.google_books.local.model.toModel
import com.example.google_books.ui.book.model.BookModel
import com.example.google_books.ui.book.model.toEntity
import javax.inject.Inject

class ClickLikeBookUseCase @Inject constructor(
    private val appRepository: AppRepository
) : UseCase<BookModel, BookModel>() {

    override suspend fun execute(parameters: BookModel): BookModel {
        if (!parameters.like) {
            appRepository.likeBook(parameters.toEntity())
        } else {
            appRepository.unLikeBook(parameters.id)
        }

        val bookModel = appRepository.findBook(parameters.id)
        return bookModel?.toModel() ?: parameters.copy(like = bookModel != null)
    }
}