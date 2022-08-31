package com.example.google_books.ui.book

import com.example.google_books.ui.book.model.BookModel

interface BookController {
    fun onClickBook(book: BookModel)
}