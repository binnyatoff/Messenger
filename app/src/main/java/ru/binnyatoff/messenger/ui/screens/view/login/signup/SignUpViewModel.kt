package ru.binnyatoff.messenger.ui.screens.view.login.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.binnyatoff.messenger.common.ActionHandler
import ru.binnyatoff.messenger.common.EventHandler
import ru.binnyatoff.messenger.data.network.Api
import ru.binnyatoff.messenger.data.network.models.query.UserRegister
import ru.binnyatoff.messenger.ui.screens.MainSharedPreferences
import ru.binnyatoff.messenger.ui.screens.view.login.signup.models.SignUpAction
import ru.binnyatoff.messenger.ui.screens.view.login.signup.models.SignUpEvent
import ru.binnyatoff.messenger.ui.screens.view.login.signup.models.SignUpViewState
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private var api: Api, private var mainSharedPreferences: MainSharedPreferences) : ViewModel(),
    EventHandler<SignUpEvent>, ActionHandler<SignUpAction> {

    private val _viewState = MutableLiveData(SignUpViewState())
    val viewState: LiveData<SignUpViewState> = _viewState

    private val _action = MutableLiveData<SignUpAction>(SignUpAction.None)
    val action: LiveData<SignUpAction> = _action


    override fun obtainEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.UserNameChanged -> userNameChanged(event.value)
            is SignUpEvent.CheckBoxClicked -> checkboxClicked(event.value)
            is SignUpEvent.LoginChanged -> loginChanged(event.value)
            is SignUpEvent.PasswordChanged -> passwordChanged(event.value)
        }
    }

    override fun obtainAction(action: SignUpAction) {

        when (action) {
            is SignUpAction.LoginButtonClicked -> registerButtonClicked()
        }
    }


    private fun registerButtonClicked() {
        val username = _viewState.value?.username
        val login = _viewState.value?.login
        val password = _viewState.value?.password
        if (username != null && login != null && password != null) {
            register(username, login, password)
        }
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


    private fun register(username: String, login: String, password: String) {
        _viewState.postValue(_viewState.value?.copy(isProgress = true))
        viewModelScope.launch {
            try {
                val response = api.registerUser(UserRegister(username, login, password))
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        mainSharedPreferences.saveToken(body.token)
                        _action.postValue(SignUpAction.TokenTrue)
                    }
                    _viewState.postValue(_viewState.value?.copy(isProgress = false))
                } else {
                    _viewState.postValue(_viewState.value?.copy(isProgress = false))
                }
            } catch (e: Exception) {
                _viewState.postValue(_viewState.value?.copy(isProgress = false))
            }
        }
    }
}