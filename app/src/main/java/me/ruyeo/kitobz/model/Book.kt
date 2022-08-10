package me.ruyeo.kitobz.model

data class Book(
    val id: Long? = null,
    val uid: String? = null,
    val image: String? = null,
    val image200: String? = null,
    val image400: String? = null,
    val name: String? = null,
    val description: String? = null,
    val author: String? = null,
    val author_books: List<AuthorBook> = arrayListOf(),
    val genres: List<String> = arrayListOf(),
    val rating: Int = 0,
    val price: Int? = null,
    val is_discount: Boolean = false,
    val discount_price: Int? = null,
    val paperBook: PaperBook? = null,
    val electronicBook: ElectronicBook? = null,
    val audioBook: AudioBook? = null,
    val feedbacks: List<Feedback> = arrayListOf(),
    val last_updated: String? = null,
    val hasPaperVersion: Boolean = false,
    val hasAudio: Boolean = false,
    val hasEVersion: Boolean = false
)

data class BookShort(
    val book_id: Long? = null,
    var uid: String? = null,
    val image: String? = null,
    val name: String? = null,
    val author: String? = null,
    val price: Int? = null,
    val is_discount: Boolean = false,
    val discount_price: Int? = null,
    val hasPaperVersion: Boolean = false,
    val hasAudio: Boolean = false,
    val hasEVersion: Boolean = false,
    val categories: List<Category> = arrayListOf()
)

data class PaperBook(
    val id: Long? = null,
    val uid: String? = null,
    val price: Int? = null,
    val is_discount: Boolean? = null,
    val discount_price: Int? = null,
    val status: String? = null,
    val published_year: Int? = null,
    val published_language: String? = null,
    val paper_type: String? = null,
    val pages_count: Int = 0,
    val book_id: Long? = null
)

data class ElectronicBook(
    val id: Long? = null,
    val uid: String? = null,
    val price: Int? = null,
    val is_discount: Boolean? = null,
    val discount_price: Int? = null,
    val published_year: Int? = null,
    val published_language: String? = null,
    val pdf: String? = null,
    val pages_count: Int = 0,
    val book_id: Long? = null
)

data class ElectronicBookShort(
    val id: Long? = null,
    val uid: String? = null,
    val price: Int? = null,
    val is_discount: Boolean? = null,
    val discount_price: Int? = null,
    val name: String? = null,
    val book_id: Long? = null
)

data class AudioBook(
    val id: Long? = null,
    val uid: String? = null,
    val price: Int? = null,
    val is_discount: Boolean? = null,
    val discount_price: Int? = null,
    val published_year: Int? = null,
    val voice_language: String? = null,
    val duration: Long? = null,
    val audios: List<Audio>? = arrayListOf(),
    val audio_fragment: String? = null,
    val book_id: Long? = null
)

data class Audio(
    val name: String? = null,
    val url: String? = null
)

data class AudioBookShort(
    val id: Long? = null,
    val uid: String? = null,
    val price: Int? = null,
    val is_discount: Boolean? = null,
    val discount_price: Int? = null,
    val name: String? = null,
    val book_id: Long? = null
)

data class AuthorBook(
    val id: Long? = null,
    val uid: String? = null,
    val image: String? = null,
    val name: String? = null
)

data class Feedback(
    val id: Long? = null,
    val uid: String? = null,
    val author: String? = null,
    val author_img: String? = null,
    val feedback: String? = null,
    val date: String? = null,
    val book_id: Long? = null
)

/**
 * Collections
 *
 * feedbacks
 *
 * */
