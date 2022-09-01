package com.example.google_books.ui.book.model

import android.content.res.Resources
import android.os.Parcelable
import com.example.google_books.R
import com.example.google_books.local.model.BookEntity
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookModel(
    val id: String,
    val title: String,
    val thumbnail: String,
    val author: String,
    val publishDate: String,
    val publisher: String,
    val price: Int,
    val description: String,
    val like: Boolean
) : Parcelable {
    fun getAuthor(resources: Resources): String = if (author.isBlank()) {
        resources.getString(R.string.none_author_information)
    } else {
        resources.getString(R.string.author, author)
    }

    fun getPublisher(resources: Resources): String = if (publisher.isBlank()) {
        resources.getString(R.string.none_information)
    } else {
        resources.getString(R.string.publisher, publisher)
    }

    fun getPrice(resources: Resources): String = if (price == 0) {
        resources.getString(R.string.none_price)
    } else {
        resources.getString(R.string.price_detail, price)
    }

    fun getDescription(resources: Resources): String = description.ifBlank {
        resources.getString(R.string.none_description)
    }

}

fun BookModel.toEntity() = BookEntity(
    id, thumbnail, title, author, publishDate, publisher, price, description, like
)
