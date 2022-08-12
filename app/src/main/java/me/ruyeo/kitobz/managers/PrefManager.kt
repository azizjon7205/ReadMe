package me.ruyeo.kitobz.managers

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import dagger.hilt.android.qualifiers.ApplicationContext
import me.ruyeo.kitobz.model.User
import javax.inject.Inject
import javax.inject.Singleton

class PrefsManager @Inject constructor(@ApplicationContext context: Context) {
    private val mySharedPref: SharedPreferences =
        context.getSharedPreferences("kitobzPref", Context.MODE_PRIVATE)


    fun setUser(user: User){
        val editor = mySharedPref.edit()
        editor.putString("user",Gson().toJson(user))
        editor.apply()
    }

    fun getUser(): User?{
        return Gson().fromJson(mySharedPref.getString("user", null), User::class.java)
    }
}