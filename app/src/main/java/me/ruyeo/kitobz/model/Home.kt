package me.ruyeo.kitobz.model

data class Home(
    val banner1: List<Banner1> = arrayListOf(),
    val banner1_count: Int = 0,
    val random_banner1: Boolean = false,
    val categories: List<Category> =  arrayListOf(),
    val show_all_visible: Boolean = true,
    val authors: List<Author> = arrayListOf(),
    val audio_books: List<AudioBook> = arrayListOf(),
    val slayder1: List<ElectronicBook> = arrayListOf(),
    val slayder1_count: Int = 0,
    val random_slayder1: Boolean = false,
    val banner2: List<String> = arrayListOf(),
    val random_banner2: Boolean = false,
    val news: List<News> = arrayListOf()
) {
}

data class Banner1(
    val id: Long? = null,
    val author: String? = null,
    val name: String? = null,
    val image: String? = null
)

data class Author(
    var uid: String? = null,
    val name: String? = null,
)

data class News(
    val id: Long? = null,
    var uid: String? = null,
    val url: String? = null,
    val text: String? = null,
    val image: String? = null
)