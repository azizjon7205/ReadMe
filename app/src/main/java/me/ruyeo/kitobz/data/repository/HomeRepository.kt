package me.ruyeo.kitobz.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import me.ruyeo.kitobz.data.remote.ApiService
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val fireStore: FirebaseFirestore
) {
    suspend fun getCategories() = apiService.getCategories()

    suspend fun getBanners(id: Int) = apiService.getBanners(id)
}