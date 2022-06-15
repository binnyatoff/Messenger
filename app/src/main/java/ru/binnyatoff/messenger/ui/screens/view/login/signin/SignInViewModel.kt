package ru.binnyatoff.messenger.ui.screens.view.login.signin

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.binnyatoff.messenger.common.EventHandler
import ru.binnyatoff.messenger.data.network.Api
import ru.binnyatoff.messenger.data.network.models.query.UserLogin
import ru.binnyatoff.messenger.ui.screens.view.login.signin.models.SignInEvent
import ru.binnyatoff.messenger.ui.screens.view.login.signin.models.SignInViewState
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private var api: Api) : ViewModel(),
    EventHandler<SignInEvent> {

    private val _viewState = MutableLiveData(SignInViewState())
    val viewState: LiveData<SignInViewState> = _viewState


    override fun obtainEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.CheckBoxClicked -> checkboxClicked(event.value)
            is SignInEvent.LoginChanged -> loginChanged(event.value)
            is SignInEvent.PasswordChanged -> passwordChanged(event.value)
            is SignInEvent.LoginButtonClicked -> loginButtonClicked()
        }
    }


    private fun loginButtonClicked() {
        login()
        Log.e("TAG", "${_viewState.value}")
    }

    private fun loginChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(login = value))
    }

    private fun passwordChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(password = value))
    }

    private fun checkboxClicked(value: Boolean) {
        _viewState.postValue(_viewState.value?.copy(rememberMeChecked = value))
    }

    private fun login() {
        viewModelScope.launch {
            Log.e("TAG", "Started")
            try {
                val response = api.loginUser(
                    UserLogin(
                        login = _viewState.value?.login ?: "",
                        password = _viewState.value?.password ?: ""
                    )
                )

                if (response.isSuccessful) {
                    Log.e("TAG", response.body().toString())
                } else {
                    Log.e("TAG", response.message())
                }
            } catch (e: Exception) {
                Log.e("TAG", "$e")
            }
        }
    }
}