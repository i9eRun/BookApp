package com.example.bookapp.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bookapp.model.Book
import com.example.bookapp.viewmodel.BookViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookScreen(viewModel: BookViewModel = viewModel()) {
    val books by viewModel.books.collectAsState()
    var showDialog by remember { mutableStateOf(false) }
    var editingBook by remember { mutableStateOf<Book?>(null) }
    var bookToDelete by remember { mutableStateOf<Book?>(null) }
    var searchQuery by remember { mutableStateOf("") }

    // 최초 진입 시 책 목록 불러오기
    LaunchedEffect(Unit) {
        viewModel.loadBooks()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        // 책 등록 버튼
        Button(onClick = {
            editingBook = null
            showDialog = true
        }) {
            Text("📚 책 등록하기")
        }

        // 검색 입력창 UI 추가
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("검색 ( 제목, 저자, 출판사 )")},
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
        )

        val filteredBooks = books.filter {
            searchQuery.isBlank() || listOf(
                it.title, it.author, it.publisher
            ).any { field -> field.contains(searchQuery, ignoreCase = true)}
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 책 목록 표시
        LazyColumn {
            items(filteredBooks) { book ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp)
                        .combinedClickable(
                            onClick = {
                                editingBook = book        // 책 수정 모드
                                showDialog = true
                            },
                            onLongClick = {
                                // viewModel.deleteBook(book) // 롱클릭 시 삭제
                                bookToDelete = book // 삭제 대상 책 저장
                            }
                        )
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(book.title, style = MaterialTheme.typography.titleLarge)
                        Text("저자: ${book.author}")
                        Text("출판사: ${book.publisher}")
                        Text("가격: ${book.price}원, 수량: ${book.quantity}")
                    }
                }
            }
        }
    }

    // 등록/수정 다이얼로그
    if (showDialog) {
        BookFormDialog(
            book = editingBook,
            onDismiss = { showDialog = false },
            onSave = {
                viewModel.addOrUpdateBook(it)
                showDialog = false
            }
        )
    }

    // 삭제 확인 다이얼로그 추가
    if (bookToDelete != null) {
        AlertDialog(
            onDismissRequest = { bookToDelete = null },
            title = { Text("삭제 확인") },
            text = { Text("정말 '${bookToDelete?.title}' 책을 삭제하시겠습니까?") },
            confirmButton = {
                Button(onClick = {
                    viewModel.deleteBook(bookToDelete!!)
                    bookToDelete = null
                }) {
                    Text("삭제")
                }
            },
            dismissButton = {
                TextButton(onClick = { bookToDelete = null }) {
                    Text("취소")
                }
            }
        )
    }
}
