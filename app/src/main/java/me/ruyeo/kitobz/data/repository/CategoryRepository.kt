package me.ruyeo.kitobz.data.repository

import me.ruyeo.kitobz.data.remote.ApiService
import javax.inject.Inject

class CategoryRepository @Inject constructor(
    private val apiService: ApiService
) {
    suspend fun getCategories() = apiService.getCategories()
}