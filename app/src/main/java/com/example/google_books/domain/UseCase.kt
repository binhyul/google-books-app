package com.example.google_books.domain

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(parameters: P): Flow<R> {
        return execute(parameters).flowOn(coroutineDispatcher)
    }

    abstract suspend fun execute(parameters: P): Flow<R>
}
