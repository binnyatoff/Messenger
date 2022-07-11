package ru.binnyatoff.messenger.ui.screens.view.messenger.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import ru.binnyatoff.messenger.ui.screens.view.messenger.chat.ChatView
import ru.binnyatoff.messenger.ui.screens.view.messenger.profile.ProfileView
import ru.binnyatoff.messenger.ui.screens.view.messenger.contacts.ContactsView
import ru.binnyatoff.messenger.ui.screens.view.messenger.contacts.ContactsViewModel
import ru.binnyatoff.messenger.ui.screens.view.messenger.dialogs.DialogsView
import ru.binnyatoff.messenger.ui.screens.view.messenger.dialogs.DialogsViewModel

@Composable
fun MessengerNavigationGraph(navController: NavHostController, appNavHostController: NavHostController) {
    NavHost(navController = navController, startDestination = NavigationBottomTree.Dialogs.name) {

        composable(NavigationBottomTree.Dialogs.name) {
            val viewModel = hiltViewModel<DialogsViewModel>()
            DialogsView(viewModel = viewModel, appNavHostController)
        }

        composable(NavigationBottomTree.Contacts.name) {
            val viewModel = hiltViewModel<ContactsViewModel>()
            ContactsView(viewModel = viewModel, appNavHostController = appNavHostController)
        }

        composable(NavigationBottomTree.Profile.name) {
            ProfileView()
        }
    }
}