package ru.binnyatoff.messenger.ui.screens.view.login.signin.models

sealed class SignInEvent {
    data class LoginChanged(val value: String) : SignInEvent()
    data class PasswordChanged(val value: String) : SignInEvent()
    data class CheckBoxClicked(val value: Boolean) : SignInEvent()
}
