package com.example.google_books.local

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Singleton
    @Provides
    fun provideBookDataBase(@ApplicationContext context: Context): BookDataBase {
        return Room.databaseBuilder(context, BookDataBase::class.java, BOOK_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    fun provideBookDao(database: BookDataBase): BookDao {
        return database.shops()
    }

    companion object {
        private const val BOOK_DATABASE_NAME = "book_data_base"
    }
}