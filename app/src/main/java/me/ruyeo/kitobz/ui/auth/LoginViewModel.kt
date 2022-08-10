package me.ruyeo.kitobz.ui.auth

import android.app.Activity
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import me.ruyeo.kitobz.data.repository.AuthRepository
import me.ruyeo.kitobz.model.LoginData
import me.ruyeo.kitobz.utils.utils.Constants.ERROR_MESSAGE
import me.ruyeo.kitobz.utils.utils.Constants.PHONE_AUTH_TIMEOUT
import me.ruyeo.kitobz.utils.utils.UiStateObject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: AuthRepository,
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private var verificationId: String? = null

    private val _loginState = MutableLiveData<LoginState>()
    val loginState: LiveData<LoginState> = _loginState


    private val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            viewModelScope.launch {
                signInWithCredential(credential)
            }
        }

        override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
            verificationId = p0
            _loginState.value = LoginState.CodeSent
        }

        override fun onVerificationFailed(p0: FirebaseException) {
            _loginState.value = LoginState.Error(p0.message ?: "Error")
            Log.d("samerror", p0.message ?: "Error")
        }
    }

    fun sendVerificationCode(number: String, activity: Activity) = viewModelScope.launch{
        _loginState.value = LoginState.Loading
        val options = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(number)
            .setTimeout(PHONE_AUTH_TIMEOUT, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(callback)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun verifyCode(code: String) {
        val credential = PhoneAuthProvider.getCredential(verificationId!!, code)
        signInWithCredential(credential)
    }

    private fun signInWithCredential(credential: PhoneAuthCredential) {
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener() { task ->
                if (task.isSuccessful) {
                    _loginState.value = LoginState.Success
                } else {
                    _loginState.value = LoginState.Error(task.exception?.message ?: "Error")
                    Log.d("samerror", task.exception?.message ?: "Error")
                }
            }
    }
}

sealed class LoginState {
    object Loading : LoginState()
    data class Error(val msg: String) : LoginState()
    object CodeSent : LoginState()
    object Success : LoginState()
}