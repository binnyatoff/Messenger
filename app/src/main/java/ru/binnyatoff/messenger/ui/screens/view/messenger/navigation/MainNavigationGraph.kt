package ru.binnyatoff.messenger.ui.screens.view.messenger.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.binnyatoff.messenger.ui.screens.view.messenger.MessagesView
import ru.binnyatoff.messenger.ui.screens.view.messenger.NavigationBottom
import ru.binnyatoff.messenger.ui.screens.view.messenger.ProfileView
import ru.binnyatoff.messenger.ui.screens.view.messenger.contacts.ContactsView
import ru.binnyatoff.messenger.ui.screens.view.messenger.contacts.ContactsViewModel

@Composable
fun MainNavigationGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationBottom.Messages.name) {
        composable(NavigationBottom.Messages.name) {
            MessagesView()
        }

        composable(NavigationBottom.Contacts.name) {
            val viewModel = hiltViewModel<ContactsViewModel>()
            ContactsView(viewModel = viewModel)
        }

        composable(NavigationBottom.Profile.name) {
            ProfileView()
        }
    }
}