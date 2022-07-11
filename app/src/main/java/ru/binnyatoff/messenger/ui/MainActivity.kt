package ru.binnyatoff.messenger.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import dagger.hilt.android.AndroidEntryPoint
import ru.binnyatoff.messenger.ui.screens.view.navigation.ApplicationScreen
import ru.binnyatoff.messenger.ui.theme.AppTheme
import ru.binnyatoff.messenger.ui.theme.MessengerTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MessengerTheme {
                val systemUiController = rememberSystemUiController()

                // Set status bar color
                val primaryBackground = AppTheme.colors.primaryBackground

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = primaryBackground,
                        darkIcons = true
                    )
                }

                ApplicationScreen()
            }
        }
    }
}