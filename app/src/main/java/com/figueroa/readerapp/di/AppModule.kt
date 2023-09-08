package com.figueroa.readerapp.di

import com.figueroa.readerapp.network.BooksAPI
import com.figueroa.readerapp.repository.BookRepository
import com.figueroa.readerapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideBookRepository(API: BooksAPI) = BookRepository(API)

    @Singleton
    @Provides
    fun provideBookAPI(): BooksAPI {
        return Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build().create(BooksAPI::class.java)
    }
}
