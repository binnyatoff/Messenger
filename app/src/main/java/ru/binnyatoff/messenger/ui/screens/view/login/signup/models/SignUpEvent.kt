package ru.binnyatoff.messenger.ui.screens.view.login.signup.models


sealed class SignUpEvent {
    data class UserNameChanged(val value: String): SignUpEvent()
    data class LoginChanged(val value: String) : SignUpEvent()
    data class PasswordChanged(val value: String) : SignUpEvent()
    data class CheckBoxClicked(val value: Boolean) : SignUpEvent()

    object LoginButtonClicked: SignUpEvent()
    object ActionButtonClicked: SignUpEvent()
}