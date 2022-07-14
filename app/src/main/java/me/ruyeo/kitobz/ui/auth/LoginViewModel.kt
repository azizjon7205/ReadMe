package me.ruyeo.kitobz.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.data.repository.AuthRepository
import me.ruyeo.kitobz.model.LoginData
import me.ruyeo.kitobz.utils.utils.Constants.ERROR_MESSAGE
import me.ruyeo.kitobz.utils.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiStateObject<LoginData>>(UiStateObject.EMPTY)
    val loginState = _loginState

    fun login(map: HashMap<String, Any>) = viewModelScope.launch {
        _loginState.value = UiStateObject.LOADING
        try {
            val response = repository.login(map)
            if (response.ok) {
                _loginState.value = UiStateObject.SUCCESS(response.data)
            } else {
                _loginState.value = UiStateObject.ERROR(response.error)
            }
        } catch (e: Exception) {
            _loginState.value = UiStateObject.ERROR(e.localizedMessage ?: ERROR_MESSAGE)
        }
    }
}