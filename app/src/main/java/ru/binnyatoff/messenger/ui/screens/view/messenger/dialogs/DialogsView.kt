package ru.binnyatoff.messenger.ui.screens.view.messenger.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import ru.binnyatoff.messenger.data.network.models.response.Dialog
import ru.binnyatoff.messenger.ui.screens.view.messenger.components.Dialog
import ru.binnyatoff.messenger.ui.screens.view.messenger.navigation.NavigationBottomTree
import ru.binnyatoff.messenger.ui.theme.AppTheme


@Composable
fun DialogsView(viewModel: DialogsViewModel, navController: NavController) {
    val viewState = viewModel.viewState.observeAsState()
    when (val state = viewState.value) {
        is DialogsState.Loaded -> DialogsLoaded(
            list = state.dialogList,
            userLogin = "TEST",
            navController = navController
        )
    }
    LaunchedEffect(key1 = Unit, block = {
        viewModel.obtainEvent(DialogEvent.ScreenInit)
    })
}

@Composable
fun DialogsLoaded(list: List<Dialog>, userLogin: String, navController: NavController) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(AppTheme.colors.secondaryBackground),
    ) {
        list.forEach { dialog ->
            item {
                if (dialog.user_one != userLogin) {
                    Dialog(
                        name = dialog.user_one
                    ) {
                        navController.navigate("chat/${dialog.dialog_key}")
                    }
                } else {
                    Dialog(name = dialog.user_two) {
                        navController.navigate("chat/${dialog.dialog_key}")
                    }

                }
            }
        }
    }
}