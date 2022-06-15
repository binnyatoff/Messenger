package ru.binnyatoff.messenger.ui.screens

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.binnyatoff.messenger.ui.navigation.NavigationTree
import ru.binnyatoff.messenger.ui.screens.view.home.HomeView
import ru.binnyatoff.messenger.ui.screens.view.home.HomeViewModel
import ru.binnyatoff.messenger.ui.screens.view.login.signin.SignInView
import ru.binnyatoff.messenger.ui.screens.view.login.signin.SignInViewModel
import ru.binnyatoff.messenger.ui.screens.view.login.signup.SignUpView
import ru.binnyatoff.messenger.ui.screens.view.login.signup.SignUpViewModel
import ru.binnyatoff.messenger.ui.screens.view.splash.SplashScreen

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavigationTree.Home.name) {
        composable(NavigationTree.Splash.name) { SplashScreen(navController) }

        composable(NavigationTree.SignIn.name) {
            val signInViewModel = hiltViewModel<SignInViewModel>()
            SignInView(signInViewModel = signInViewModel, navController)
        }

        composable(NavigationTree.SignUp.name) {
            val signUpViewModel = hiltViewModel<SignUpViewModel>()
            SignUpView(signUpViewModel = signUpViewModel, navController)
        }

        composable(NavigationTree.Home.name) {
            val homeViewModel = hiltViewModel<HomeViewModel>()
            HomeView(viewModel = homeViewModel)
        }
    }
}