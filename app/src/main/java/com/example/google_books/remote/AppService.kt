package com.example.google_books.remote

import com.example.google_books.remote.model.ListContentsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AppService {

    @GET("/books/v1/volumes")
    suspend fun getListContents(
        @Query(value = "q") text: String,
        @Query("startIndex") startIndex: String,
        @Query("maxResults") size: String,
        @Query("filter") filter: String = "paid-ebooks"
    ): ListContentsResponse

}

