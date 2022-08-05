package me.ruyeo.kitobz.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.data.repository.AuthRepository
import me.ruyeo.kitobz.model.User
import me.ruyeo.kitobz.utils.utils.Constants
import me.ruyeo.kitobz.utils.utils.UiStateObject
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {

    private val _registerState = MutableStateFlow<UiStateObject<User>>(UiStateObject.EMPTY)
    val registerState = _registerState

    fun register(map: HashMap<String, Any>) = viewModelScope.launch {
        _registerState.value = UiStateObject.LOADING
        try {
            val response = repository.register(map)
            if (response.ok) {
                _registerState.value = UiStateObject.SUCCESS(response.data)
            } else {
                _registerState.value = UiStateObject.ERROR(response.error)
            }
        } catch (e: Exception) {
            _registerState.value = UiStateObject.ERROR(e.localizedMessage ?: Constants.ERROR_MESSAGE)
        }
    }
}