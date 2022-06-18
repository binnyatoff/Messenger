package ru.binnyatoff.messenger.ui.screens.view.messenger.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.binnyatoff.messenger.ui.screens.view.messenger.NavigationBottom


sealed class BottomNavigationItems(var title: String, var screen_route: String) {
    object Messages : BottomNavigationItems("Messages", NavigationBottom.Messages.name)
    object Contacts : BottomNavigationItems("Contacts", NavigationBottom.Contacts.name)
    object Profile : BottomNavigationItems("Profile", NavigationBottom.Profile.name)
}

@Composable
fun MainBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavigationItems.Messages,
        BottomNavigationItems.Contacts,
        BottomNavigationItems.Profile
    )
    BottomNavigation() {

        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { item ->
            BottomNavigationItem(
                selected = currentRoute == item.screen_route,
                label = { Text(text = item.title) },
                onClick = { navController.navigate(item.screen_route) },
                icon = { /*TODO*/ }
            )
        }
    }

}