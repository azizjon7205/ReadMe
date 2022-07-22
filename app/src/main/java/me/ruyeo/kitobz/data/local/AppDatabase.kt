package me.ruyeo.kitobz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import me.ruyeo.kitobz.data.local.dao.BookDao
import me.ruyeo.kitobz.data.local.entity.Book

@Database(entities = [Book::class], version = 1,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getBookingDao(): BookDao
}