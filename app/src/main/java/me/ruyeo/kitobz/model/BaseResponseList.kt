package me.ruyeo.kitobz.model

data class BaseResponseList<T>(
    val ok: Boolean,
    val data: List<T>,
    val error: String
)