package me.ruyeo.kitobz.data.repository

import me.ruyeo.kitobz.data.remote.ApiService
import javax.inject.Inject

class AuthRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun register(map: HashMap<String,Any>) = apiService.register(map)

    suspend fun login(map: HashMap<String,Any>) = apiService.login(map)
}