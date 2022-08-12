package me.ruyeo.kitobz.model

data class Discuss(
    val id: String? = null,
    val header: String? = null,
    val avatar: String? = null,
    val owner_name: String? = null,
    val messages_count: Int = 0,
    val created_date: String? = null,
    var followed: Boolean = false
)

data class DiscussDetails(
    val id: String? = null,
    val header: String? = null,
    val avatar: String? = null,
    val owner_name: String? = null,
    val discuss_theme: String? = null,
    val created_date: String? = null,
    val answers_count: Int = 0,
    val messages_count: Int = 0,
    val answers: List<Answer> = arrayListOf(),
    var followed: Boolean = false
)

data class Answer(
    val id: String? = null,
    val owner_image: String? = null,
    val owner_name: String? = null,
    val date: String? = null,
    val messages_count: Int = 0,
    val message: String? = null,
    val replies: ArrayList<Reply> = arrayListOf()
)

data class Reply(
    val id: String? = null,
    val owner_image: String? = null,
    val owner_name: String? = null,
    val date: String? = null,
    val message: String? = null
)

