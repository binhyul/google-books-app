package com.example.google_books.remote.model

import com.google.gson.annotations.SerializedName

data class ListContentsResponse(
    @SerializedName("totalItems") val totalItems: Int,
    @SerializedName("items") val items: List<BookItem>?,
)