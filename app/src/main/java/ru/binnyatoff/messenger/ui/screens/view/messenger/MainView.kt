package ru.binnyatoff.messenger.ui.screens.view.messenger

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import ru.binnyatoff.messenger.ui.screens.view.messenger.navigation.MainBottomNavigation
import ru.binnyatoff.messenger.ui.screens.view.messenger.navigation.MainNavigationGraph


enum class NavigationBottom {
    Dialogs, Contacts, Profile
}

@Composable
fun MainView(
) {
    val navController = rememberNavController()
    Scaffold(bottomBar = { MainBottomNavigation(navController = navController) })
    {
        MainNavigationGraph(navController)
    }
}

