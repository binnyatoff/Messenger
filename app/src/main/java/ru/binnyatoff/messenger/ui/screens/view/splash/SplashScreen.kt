package ru.binnyatoff.messenger.ui.screens.view.splash

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import ru.binnyatoff.messenger.ui.screens.view.navigation.NavigationTree

@Composable
fun SplashScreen(viewModel: SplashViewModel, navController: NavController) {
    val viewState = viewModel.viewState.observeAsState()

    LaunchedEffect(key1 = viewState.value, block = {
        when (viewState.value) {
            is SplashState.SignUpState -> {
                Log.e("TAG", "SignUpState")
                navigate(navController, NavigationTree.SignUp.name)
            }
            is SplashState.MainState -> {
                Log.e("TAG", "MainState")
                navigate(navController, NavigationTree.Main.name)
            }
            is SplashState.None -> {
                Log.e("TAG", "none")
                viewModel.obtainEvent(SplashEvent.ScreenInit)
            }
        }
    })

}

fun navigate(navController: NavController, value: String) {
    navController.navigate(value) {
        popUpTo(NavigationTree.Splash.name) {
            inclusive = true
        }
    }
}