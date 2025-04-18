package com.example.bookapp.model

data class Book(
    var id: Long? = null,
    var author: String,
    var price: Int,
    var publisher: String,
    var quantity: Int,
    var title: String
)
