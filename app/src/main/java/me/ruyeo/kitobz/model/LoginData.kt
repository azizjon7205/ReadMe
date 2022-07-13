package me.ruyeo.kitobz.model

data class LoginData(
    val user: User,
    val token: String,
    val expire: Int
)