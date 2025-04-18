package com.example.bookapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookapp.data.RetrofitClient
import com.example.bookapp.model.Book
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BookViewModel : ViewModel() {

    // 🔄 책 목록을 저장하는 상태 흐름
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    // 📥 서버에서 책 목록 불러오기
    fun loadBooks() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.bookApi.getBooks()
                println("📥 가져온 책 수: ${response.size}")
                _books.value = response
            } catch (e: Exception) {
                e.printStackTrace() // 콘솔에서 오류 메시지 확인
            }
        }
    }

    // ➕ 책 등록 또는 수정
    fun addOrUpdateBook(book: Book) {
        viewModelScope.launch {
            try {
                if (book.id == null) {
                    RetrofitClient.bookApi.addBook(book)
                } else {
                    RetrofitClient.bookApi.updateBook(book.id!!, book)
                }
                loadBooks() // 저장 후 목록 갱신
            } catch (e: Exception) {
                // 예외 처리 가능
            }
        }
    }

    // ❌ 책 삭제
    fun deleteBook(book: Book) {
        viewModelScope.launch {
            try {
                RetrofitClient.bookApi.deleteBook(book.id!!)
                loadBooks() // 삭제 후 목록 갱신
            } catch (e: Exception) {
                // 삭제 실패 시 처리
            }
        }
    }
}
