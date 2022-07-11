package ru.binnyatoff.messenger.ui.screens.view.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.binnyatoff.messenger.ui.screens.view.login.signin.SignInView
import ru.binnyatoff.messenger.ui.screens.view.login.signin.SignInViewModel
import ru.binnyatoff.messenger.ui.screens.view.login.signup.SignUpView
import ru.binnyatoff.messenger.ui.screens.view.login.signup.SignUpViewModel
import ru.binnyatoff.messenger.ui.screens.view.messenger.MainView
import ru.binnyatoff.messenger.ui.screens.view.messenger.chat.ChatView
import ru.binnyatoff.messenger.ui.screens.view.messenger.chat.ChatViewModel
import ru.binnyatoff.messenger.ui.screens.view.splash.SplashScreen
import ru.binnyatoff.messenger.ui.screens.view.splash.SplashViewModel

@Composable
fun ApplicationScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NavigationTree.Splash.name) {
        composable(NavigationTree.Splash.name) {
            val splashViewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(navController = navController, viewModel = splashViewModel)
        }

        composable(NavigationTree.SignIn.name) {
            val signInViewModel = hiltViewModel<SignInViewModel>()
            SignInView(signInViewModel = signInViewModel, navController)
        }

        composable(NavigationTree.SignUp.name) {
            val signUpViewModel = hiltViewModel<SignUpViewModel>()
            SignUpView(signUpViewModel = signUpViewModel, navController)
        }

        composable(NavigationTree.Main.name) {
            MainView(appNavHostController = navController)
        }
        composable("chat/{dialogKey}") {
            val viewModel = hiltViewModel<ChatViewModel>()
            ChatView(it.arguments?.getString("dialogKey", "") ?: "", viewModel = viewModel)
        }
    }
}