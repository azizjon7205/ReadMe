package me.ruyeo.kitobz.data.remote

import me.ruyeo.kitobz.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //registration
    @POST("user/register")
    suspend fun register(@Body map: HashMap<String,Any>): BaseResponseObject<User>

    @POST("user/login")
    suspend fun login(@Body map: HashMap<String, Any>): BaseResponseObject<LoginData>

    //book
    @GET("books")
    suspend fun getBooks(): BaseResponseList<Book>

    //category
    @GET("cats")
    suspend fun getCategories(): BaseResponseList<Category>

}