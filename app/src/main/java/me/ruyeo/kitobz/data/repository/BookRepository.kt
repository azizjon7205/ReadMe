package me.ruyeo.kitobz.data.repository

import me.ruyeo.kitobz.data.remote.ApiService
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getBooks() = apiService.getBooks()
}