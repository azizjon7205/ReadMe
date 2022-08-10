package me.ruyeo.kitobz.model

data class Category(
    val count: Int? = null,
    val id: Int? = null,
    var uid: String? = null,
    val image: String? = null,
    val last_updated: String? = null,
    val name: String? = null,
    val children: List<Category>? = null
)