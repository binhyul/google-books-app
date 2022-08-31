package com.example.google_books.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.google_books.ui.book.model.BookModel

@Entity(tableName = "book")
data class BookEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val thumbnail: String,
    val title: String,
    val author: String,
    val price: String,
    val description: String,
    val like: Boolean
)

fun BookEntity.toModel() = BookModel(
    id = id,
    title = title,
    thumbnail = thumbnail,
    author = author,
    price = price,
    description = description,
    like = true
)