package me.ruyeo.kitobz.managers

import me.ruyeo.kitobz.model.User

object AuthManager {
    var user: User? = null
    var isAuthorized = user != null
}