package me.ruyeo.kitobz.model

data class Discuss(
    val uid: String? = null,
    val header: String? = null,
    val avatar: String? = null,
    val owner_name: String? = null,
    val discuss_theme: String? = null,
    val messages_count: Int = 0,
    val answers_count: Int = 0,
    val created_date: String? = null,
    var followed: Boolean = false
)

data class Owner(
    var uid: String? = null,
    val owner_name: String? = null,
    val owner_image: String? = null,
    val owner_uid: String? = null
)

data class DiscussDetails(
    val uid: String? = null,
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
    val uid: String? = null,
    val owner_image: String? = null,
    val owner_name: String? = null,
    val owner_uid: String? = null,
    val date: String? = null,
    val replies_count: Int = 0,
    val message: String? = null,
    val replies: List<Reply> = arrayListOf()
)

data class Reply(
    val uid: String? = null,
    val owner_image: String? = null,
    val owner_name: String? = null,
    val owner_uid: String? = null,
    val date: String? = null,
    val message: String? = null
)

