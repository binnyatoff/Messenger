package ru.binnyatoff.messenger.ui.screens.view.home.models

import ru.binnyatoff.messenger.data.network.models.response.ListUsers

sealed class HomeViewState {
    object Loading : HomeViewState()
    class Loaded(val list : ListUsers) : HomeViewState()
    class Empty(): HomeViewState()
    class Error(e: String): HomeViewState()
}