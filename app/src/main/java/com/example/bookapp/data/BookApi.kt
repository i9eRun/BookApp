package com.example.bookapp.data

import com.example.bookapp.model.Book
import retrofit2.Response
import retrofit2.http.*

interface BookApi {

    @GET("/api/books")
    suspend fun getBooks(): List<Book>

    @POST("/api/books")
    suspend fun addBook(@Body book: Book): Response<Book>

    @PUT("/api/books/{id}")
    suspend fun updateBook(@Path("id") id: Long, @Body book: Book): Response<Book>

    @DELETE("/api/books/{id}")
    suspend fun deleteBook(@Path("id") id: Long): Response<Void>

}