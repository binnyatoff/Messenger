package ru.binnyatoff.messenger.ui.screens.view.splash

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import ru.binnyatoff.messenger.ui.navigation.NavigationTree

@Composable
fun SplashScreen(navController: NavController) {
    Text(text = "Hello from Splah")
    LaunchedEffect(key1 = Unit, block = {
        navController.navigate(NavigationTree.SignUp.name) {
            popUpTo(NavigationTree.Splash.name) {
                inclusive = true
            }
        }
    })
}