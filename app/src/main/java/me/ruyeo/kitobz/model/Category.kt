package me.ruyeo.kitobz.model

data class Category(
    val count: Int,
    val id: Int,
    val image: String,
    val last_updated: String,
    val name: String,
    val children: List<Category>
)