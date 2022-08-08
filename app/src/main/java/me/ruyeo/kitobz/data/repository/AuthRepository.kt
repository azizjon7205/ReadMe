package me.ruyeo.kitobz.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import me.ruyeo.kitobz.data.remote.ApiService
import me.ruyeo.kitobz.utils.utils.Constants.BOOKS
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun register(map: HashMap<String,Any>) = apiService.register(map)

    suspend fun login(map: HashMap<String,Any>) = apiService.login(map)
}