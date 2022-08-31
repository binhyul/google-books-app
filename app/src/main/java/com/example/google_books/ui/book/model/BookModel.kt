package com.example.google_books.ui.book.model

import android.os.Parcelable
import com.example.google_books.local.model.BookEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel(
    val id: String,
    val title: String,
    val thumbnail: String,
    val author: String,
    val price: String,
    val description: String,
    val like: Boolean
) : Parcelable

fun BookModel.toEntity() = BookEntity(
    id, thumbnail, title, author, price, description, like
)
