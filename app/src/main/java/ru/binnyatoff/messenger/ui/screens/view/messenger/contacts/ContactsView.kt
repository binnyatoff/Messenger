package ru.binnyatoff.messenger.ui.screens.view.messenger.contacts

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import ru.binnyatoff.messenger.ui.screens.view.messenger.components.Dialog
import ru.binnyatoff.messenger.ui.theme.AppTheme
import ru.binnyatoff.messenger.R
import ru.binnyatoff.messenger.ui.screens.view.messenger.navigation.NavigationBottomTree


@Composable
fun ContactsView(viewModel: ContactsViewModel, appNavHostController: NavHostController) {
    val viewState = viewModel.viewState.observeAsState()
    when (val state = viewState.value) {
        is ContactsState.Loaded -> ContactsLoaded(
            state.listUsers.listUsers,
            appNavHostController = appNavHostController,
            viewModel
        )
        is ContactsState.Empty -> Empty("Not Found")
        is ContactsState.Error -> Empty(state.error)
        //is ContactsState.Loading
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.obtainEvent(ContactsEvent.ScreenInit)
    })
}

@Composable
fun ContactsLoaded(
    list: List<String>,
    appNavHostController: NavHostController,
    viewModel: ContactsViewModel
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
            .background(AppTheme.colors.secondaryBackground),
    ) {
        list.forEach { message ->
            item {
                Dialog(name = message) {
                    viewModel.obtainEvent(ContactsEvent.ContactClicked(message))
                    appNavHostController.navigate(NavigationBottomTree.Chat.name)
                }
            }
        }
    }
}

@Composable
fun Empty(text: String) {
    val image = painterResource(id = R.drawable.ic_ufo)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = image, contentDescription = "Empty")
        Text(text = text)
    }
}