package com.example.bookapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookapp.databinding.ItemBookBinding
import com.example.bookapp.model.Book

class BookAdapter(
    private var books: List<Book>
) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {

    inner class BookViewHolder(val binding: ItemBookBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val book = books[position]
        with(holder.binding) {
            tvTitle.text = book.title
            tvAuthor.text = "저자: ${book.author}"
            tvPublisher.text = "출판사: ${book.publisher}"
            tvPrice.text = "가격: ${book.price}원"
            tvQty.text = "수량: ${book.quantity}권"
        }
    }

    override fun getItemCount(): Int = books.size

    fun updateBooks(newBooks: List<Book>) {
        books = newBooks
        notifyDataSetChanged()
    }
}
