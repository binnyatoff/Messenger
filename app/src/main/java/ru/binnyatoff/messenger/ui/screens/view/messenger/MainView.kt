package ru.binnyatoff.messenger.ui.screens.view.messenger

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.binnyatoff.messenger.ui.screens.view.messenger.navigation.MainBottomNavigation
import ru.binnyatoff.messenger.ui.screens.view.messenger.navigation.MessengerNavigationGraph

@Composable
fun MainView(appNavHostController: NavHostController
) {
    val navController = rememberNavController()
    Scaffold(bottomBar = { MainBottomNavigation(navController = navController) })
    {
        MessengerNavigationGraph(navController = navController, appNavHostController = appNavHostController)
    }
}

