package ru.binnyatoff.messenger.ui.screens.view.messenger.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import ru.binnyatoff.messenger.R


sealed class BottomNavigationItems(var title: String, var screen_route: String, var icon: Int) {
    object Dialogs : BottomNavigationItems("Dialogs", NavigationBottomTree.Dialogs.name, R.drawable.ic_shape)
    object Contacts : BottomNavigationItems("Contacts", NavigationBottomTree.Contacts.name, R.drawable.ic_people)
    object Profile : BottomNavigationItems("Profile", NavigationBottomTree.Profile.name,R.drawable.ic_account)
}

@Composable
fun MainBottomNavigation(navController: NavController) {
    val items = listOf(
        BottomNavigationItems.Dialogs,
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
                icon = { Icon(painter = painterResource(id = item.icon), contentDescription = item.title) }
            )
        }
    }

}