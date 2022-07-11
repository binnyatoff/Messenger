package ru.binnyatoff.messenger.ui.screens.view.login.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.binnyatoff.messenger.common.ActionHandler
import ru.binnyatoff.messenger.common.EventHandler
import ru.binnyatoff.messenger.data.network.Api
import ru.binnyatoff.messenger.data.network.models.query.UserLogin
import ru.binnyatoff.messenger.ui.screens.MainSharedPreferences
import ru.binnyatoff.messenger.ui.screens.view.login.signin.models.SignInAction
import ru.binnyatoff.messenger.ui.screens.view.login.signin.models.SignInEvent
import ru.binnyatoff.messenger.ui.screens.view.login.signin.models.SignInViewState
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private var api: Api,
    private var mainSharedPreferences: MainSharedPreferences
) : ViewModel(),
    EventHandler<SignInEvent>, ActionHandler<SignInAction> {

    private val _viewState = MutableLiveData(SignInViewState())
    val viewState: LiveData<SignInViewState> = _viewState

    private val _action = MutableLiveData<SignInAction>(SignInAction.None)
    val action: LiveData<SignInAction> = _action


    override fun obtainEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.CheckBoxClicked -> checkboxClicked(event.value)
            is SignInEvent.LoginChanged -> loginChanged(event.value)
            is SignInEvent.PasswordChanged -> passwordChanged(event.value)
        }
    }

    override fun obtainAction(action: SignInAction) {
        when (action) {
            is SignInAction.LoginButtonClicked -> loginButtonClicked()
        }
    }


    private fun loginButtonClicked() {
        val login = _viewState.value?.login
        val password = _viewState.value?.password
        if (login != null && password != null) {
            login(login, password)
        }
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

    private fun login(login: String, password: String) {
        _action.postValue(SignInAction.None)
        _viewState.postValue(_viewState.value?.copy(isProgress = true))
        viewModelScope.launch {
            try {
                val userLogin = UserLogin(login, password)
                val response = api.loginUser(userLogin)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        mainSharedPreferences.saveToken(body.token)
                        _viewState.postValue(_viewState.value?.copy(isProgress = false))
                        _action.postValue(SignInAction.TokenTrue)
                    }
                } else {
                    _viewState.postValue(_viewState.value?.copy(isProgress = false))
                }
            } catch (e: Exception) {
                _viewState.postValue(_viewState.value?.copy(isProgress = false))
            }
        }
    }

}