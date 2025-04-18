package com.example.bookapp.ui

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import com.example.bookapp.model.Book

@Composable
fun BookFormDialog(
    book: Book?,
    onDismiss: () -> Unit,
    onSave: (Book) -> Unit
) {
    // 상태 초기화
    var title by remember { mutableStateOf(book?.title ?: "") }
    var author by remember { mutableStateOf(book?.author ?: "") }
    var publisher by remember { mutableStateOf(book?.publisher ?: "") }
    var price by remember { mutableStateOf(book?.price?.toString() ?: "") }
    var quantity by remember { mutableStateOf(book?.quantity?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = if (book == null) "책 등록" else "책 수정")
        },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("제목") })
                OutlinedTextField(value = author, onValueChange = { author = it }, label = { Text("저자") })
                OutlinedTextField(value = publisher, onValueChange = { publisher = it }, label = { Text("출판사") })
                OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("가격") })
                OutlinedTextField(value = quantity, onValueChange = { quantity = it }, label = { Text("수량") })
            }
        },
        confirmButton = {
            Button(onClick = {
                onSave(
                    Book(
                        id = book?.id,
                        title = title,
                        author = author,
                        publisher = publisher,
                        price = price.toIntOrNull() ?: 0,
                        quantity = quantity.toIntOrNull() ?: 0
                    )
                )
            }) {
                Text("저장")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("취소")
            }
        }
    )
}
