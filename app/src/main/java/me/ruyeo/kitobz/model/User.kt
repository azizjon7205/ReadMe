package me.ruyeo.kitobz.model

data class User(
    val uid: Long? = null,
    val phone: String? = null,
    val name: String? = null,
    val password: String? = null,
    val image: String? = null,
    val device_id: String? = null,
    val device_token: String? = null,
    val device_type: String? = null,
    val language: String? = null,
    val notifications: Notifications? = null
)

data class Notifications(
    val news: Boolean = true,
    val feedbacks: Boolean = true
)

/**
 * Collections
 *
 * favorites
 * feedbacks
 * orders(history)
 * cart
 * my_library: ebooks, audio_books
 *
 * */
