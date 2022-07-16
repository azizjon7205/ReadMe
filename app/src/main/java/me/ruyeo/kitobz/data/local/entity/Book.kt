package me.ruyeo.kitobz.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey
    val id: Int,
    val name: String
)