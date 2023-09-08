package com.figueroa.readerapp.repository

import com.figueroa.readerapp.data.Resource
import com.figueroa.readerapp.model.Item
import com.figueroa.readerapp.network.BooksAPI
import javax.inject.Inject

class BookRepository @Inject constructor(private val API: BooksAPI) {

    suspend fun getBooks(searchQuery: String): Resource<List<Item>> {
        return try {
            Resource.Loading(data = true)
            val itemList = API.getAllBooks(searchQuery).items
            if (itemList.isNotEmpty()) {
                Resource.Loading(data = false)
            }
            Resource.Success(data = itemList)
        }catch (exception: Exception) {
            Resource.Error(message = exception.message.toString())
        }
    }
    suspend fun getBookInfo(bookId: String): Resource<Item> {
        val response = try {
            Resource.Loading(data = true)
            API.getBookInfo(bookId)
        }catch (exception: Exception) {
            return Resource.Error(message = "An error occurred ${exception.message.toString()}")
        }
        Resource.Loading(data = false)
        return Resource.Success(data = response)
    }
    //  private val dataOrException = DataOrException<List<Item>, Boolean, Exception>()
    //  private val bookInfoDataOrException = DataOrException<Item, Boolean, Exception>()
    //  suspend fun getBooks(searchQuery: String): DataOrException<List<Item>, Boolean, Exception> {
    //      try {
    //          dataOrException.loading = true
    //          dataOrException.data = API.getAllBooks(searchQuery).items
    //          if (dataOrException.data!!.isNotEmpty()) {
    //              dataOrException.loading = false
    //          }
    //      } catch (e: Exception) {
    //          dataOrException.e = e
    //      }
    //      return dataOrException
    //  }
    //
    //  suspend fun getBookInfo(bookId: String): DataOrException<Item, Boolean, Exception> {
    //      val response = try {
    //          bookInfoDataOrException.loading = true
    //          bookInfoDataOrException.data = API.getBookInfo(bookId = bookId)
    //          if (bookInfoDataOrException.toString().isNotEmpty()) {
    //              bookInfoDataOrException.loading = false
    //          } else {
    //          }
    //      } catch (e: Exception) {
    //          bookInfoDataOrException.e = e
    //      }
    //      return bookInfoDataOrException
    //  }
}
