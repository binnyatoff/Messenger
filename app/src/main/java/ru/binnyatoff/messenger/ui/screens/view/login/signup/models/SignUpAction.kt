package ru.binnyatoff.messenger.ui.screens.view.login.signup.models

sealed class SignUpAction{
    object LoginButtonClicked: SignUpAction()
    object TokenTrue: SignUpAction()
    object None: SignUpAction()
}