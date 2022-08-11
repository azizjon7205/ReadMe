package me.ruyeo.kitobz.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import me.ruyeo.kitobz.data.remote.ApiService
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val apiService: ApiService,
    private val fireStore: FirebaseFirestore
) {
    suspend fun getBooks() = apiService.getBooks()
}