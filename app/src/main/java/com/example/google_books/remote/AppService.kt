package com.example.google_books.remote

import com.example.google_books.ui.book.BookModel
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

interface AppService {

    @GET("/books/v1/volumes")
    suspend fun getListContents(
        @Query(value = "q") text: String,
        @Query("startIndex") startIndex: String,
        @Query("maxResults") size: String
    ): ListContentsResponse

}


data class ListContentsResponse(
    @SerializedName("totalItems") val totalItems: Int,
    @SerializedName("items") val items: List<BookItem>?,
)

data class BookItem(
    @SerializedName("id") val id: String?,
    @SerializedName("volumeInfo") val volumeInfo: VolumeInfo?
)

fun BookItem.toEntity() = BookModel(
    id.orEmpty(),
    volumeInfo?.title.orEmpty(),
    volumeInfo?.imageLinks?.thumbnail.orEmpty(),
    volumeInfo?.authors?.joinToString(", ").orEmpty(),
    volumeInfo?.publishedDate.orEmpty(),
    volumeInfo?.infoLink.orEmpty()
)

data class VolumeInfo(
    @SerializedName("title") val title: String?,
    @SerializedName("publishedDate") val publishedDate: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("authors") val authors: List<String>?,
    @SerializedName("imageLinks") val imageLinks: BookVolumeImage?,
    @SerializedName("infoLink") val infoLink: String?
)

data class BookVolumeImage(
    @SerializedName("smallThumbnail") val smallThumbnail: String?,
    @SerializedName("thumbnail") val thumbnail: String?
)