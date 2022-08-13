package com.example.google_books.remote.source

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindAppRemoteDataSource(
        bindAppRemoteDataSource: AppRemoteDataSourceImpl
    ): AppRemoteDataSource

}