package me.ruyeo.kitobz.managers

import com.google.firebase.firestore.FirebaseFirestore

private val HOME_PATH = "home_page"

private val USER_PATH = "users"
private val FEEDBACK_PATH = "feedbacks"
private val FAVORITES_PATH = "feedbacks"
private val ORDER_PATH = "orders"
private val CART_PATH = "cart"

private val BOOK_PATH = "books"
private val ELECTRONIC_BOOK_PATH = "electronic_books"
private val AUDIO_BOOK_PATH = "audio_books"
private val AUTHOR_BOOK_PATH = "author_books"
private val AUTHOR_PATH = "authors"
private val CATEGORY_PATH = "categories"
private val NEWS_PATH = "news"

object DatabaseManager {
    private val database = FirebaseFirestore.getInstance()



}