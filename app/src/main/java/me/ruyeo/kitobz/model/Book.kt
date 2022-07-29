package me.ruyeo.kitobz.model

data class Book(
    val author: String? = null,
    val badges: List<Badge>? = null,
    val discount_percent: Int? = null,
    val discount_price: Any? = null,
    val id: Int? = null,
    val image: String? = null,
    val image200: String? = null,
    val is_discount: Boolean = false,
    val is_like: Boolean? = null,
    val last_updated: String? = null,
    val name: String? = null,
    val pdf: String? = null,
    val price: Int? = null,
    val rating: Int? = null,
    val status: String? = null,
    val status_id: Int? = null,
    val hasAudio: Boolean = false,
    val hasEVersion: Boolean = false,
    val hasPaperVersion: Boolean = false
)