package ru.binnyatoff.messenger.ui.screens.view.login.signin.models

sealed class SignInAction {
    object LoginButtonClicked: SignInAction()
    object TokenTrue: SignInAction()
    object None: SignInAction()
}