package com.example.google_books.domain

import com.example.google_books.data.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoadSearchResultUseCase @Inject constructor(
    private val appRepository: AppRepository
) {
    fun invoke(): Flow<Int> {
        return appRepository.getTotalBooksCount()
    }
}