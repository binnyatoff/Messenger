package ru.binnyatoff.messenger.ui.screens.view.login.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.binnyatoff.messenger.common.EventHandler
import ru.binnyatoff.messenger.data.network.Api
import ru.binnyatoff.messenger.data.network.models.query.UserRegister
import ru.binnyatoff.messenger.ui.screens.view.login.signup.models.SignUpEvent
import ru.binnyatoff.messenger.ui.screens.view.login.signup.models.SignUpViewState
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private var api: Api) : ViewModel(),
    EventHandler<SignUpEvent> {

    private val _viewState = MutableLiveData(SignUpViewState())
    val viewState: LiveData<SignUpViewState> = _viewState


    override fun obtainEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.UserNameChanged -> userNameChanged(event.value)
            is SignUpEvent.CheckBoxClicked -> checkboxClicked(event.value)
            is SignUpEvent.LoginChanged -> loginChanged(event.value)
            is SignUpEvent.PasswordChanged -> passwordChanged(event.value)
            is SignUpEvent.LoginButtonClicked -> registerButtonClicked()
        }
    }


    private fun registerButtonClicked() {
        register()
        Log.e("TAG", "${_viewState.value}")
    }

    private fun userNameChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(username = value))
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

    private fun register() {
        _viewState.postValue(_viewState.value?.copy(isProgress = true))
        viewModelScope.launch {
            Log.e("TAG", "Started")
            try {
                val response = api.registerUser(
                    UserRegister(
                        username = _viewState.value?.username ?: "",
                        login = _viewState.value?.login ?: "",
                        password = _viewState.value?.password ?: "",
                    )
                )
                if (response.isSuccessful) {
                    _viewState.postValue(_viewState.value?.copy(isProgress = false))
                    Log.e("TAG", response.body().toString())
                } else {
                    _viewState.postValue(_viewState.value?.copy(isProgress = false))
                    Log.e("TAG", response.message())
                }
            } catch (e: Exception) {
                _viewState.postValue(_viewState.value?.copy(isProgress = false))
                Log.e("TAG", "$e")
            }
        }
    }
}