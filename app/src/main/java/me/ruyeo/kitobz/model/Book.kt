package me.ruyeo.kitobz.model

data class Book(
    val author: String,
    val badges: List<Badge>,
    val discount_percent: Int,
    val discount_price: Any,
    val id: Int,
    val image: String,
    val image200: String,
    val is_discount: Boolean,
    val is_like: Boolean,
    val last_updated: String,
    val name: String,
    val pdf: String,
    val price: Int,
    val rating: Int,
    val status: String,
    val status_id: Int
)