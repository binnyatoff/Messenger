package ru.binnyatoff.messenger.ui.screens.view.login.signup.models

data class SignUpViewState(
    val username:String ="",
    val login: String = "",
    val password: String = "",
    val rememberMeChecked: Boolean = false,
    val isProgress:Boolean = false
)





