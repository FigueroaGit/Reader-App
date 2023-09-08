package com.figueroa.readerapp.screens.details

import androidx.lifecycle.ViewModel
import com.figueroa.readerapp.data.Resource
import com.figueroa.readerapp.model.Item
import com.figueroa.readerapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return repository.getBookInfo(bookId)
    }
}
