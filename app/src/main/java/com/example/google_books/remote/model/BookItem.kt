package com.example.google_books.remote.model

import com.example.google_books.ui.book.model.BookModel
import com.google.gson.annotations.SerializedName

data class BookItem(
    @SerializedName("id") val id: String,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo?,
    @SerializedName("saleInfo") val saleInfo: SaleInfo?
)

fun BookItem.toModel(like: Boolean = false) = BookModel(
    id,
    volumeInfo?.title.orEmpty(),
    volumeInfo?.imageLinks?.thumbnail.orEmpty(),
    volumeInfo?.authors?.joinToString(", ").orEmpty(),
    (saleInfo?.listPrice?.amount ?: 0).toString(),
    volumeInfo?.description.orEmpty(),
    like
)

data class VolumeInfo(
    @SerializedName("title") val title: String?,
    @SerializedName("publishedDate") val publishedDate: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("authors") val authors: List<String>?,
    @SerializedName("imageLinks") val imageLinks: BookVolumeImage?,
)


data class SaleInfo(
    @SerializedName("listPrice") val listPrice: Price?,
)

data class Price(
    @SerializedName("amount") val amount: Int?
)


class BookVolumeImage(
    @SerializedName("smallThumbnail") val smallThumbnail: String?,
    @SerializedName("thumbnail") val thumbnail: String?
)