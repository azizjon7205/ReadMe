package me.ruyeo.kitobz.ui.auth

import androidx.lifecycle.ViewModel
import me.ruyeo.kitobz.data.repository.AuthRepository
import javax.inject.Inject

class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

}