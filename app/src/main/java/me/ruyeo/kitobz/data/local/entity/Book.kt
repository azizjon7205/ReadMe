package me.ruyeo.kitobz.data.local.entity

import androidx.room.Entity

@Entity
data class Book(
    val id: Int,
    val name: String
)