package me.ruyeo.kitobz.model

data class BaseResponseObject<T>(
    val ok: Boolean,
    val data: T,
    val error: String
)