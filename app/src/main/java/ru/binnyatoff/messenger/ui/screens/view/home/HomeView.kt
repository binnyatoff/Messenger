package ru.binnyatoff.messenger.ui.screens.view.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import ru.binnyatoff.messenger.ui.screens.view.home.models.HomeViewState

@Composable
fun HomeView(
    viewModel: HomeViewModel
) {
    val viewState = viewModel.viewState.observeAsState()

    Button(onClick = { viewModel.getListUsers() }) {
        Text(text = "Click ME")
    }
    when(val state = viewState.value){
        is HomeViewState.Loaded -> UserListView(users = state.list.listUsers)
    }
}

@Composable
fun UserListView(
    users : List<String>
){
    LazyColumn {
        items(users) { users ->
            Text(text = users)
        }
    }
}