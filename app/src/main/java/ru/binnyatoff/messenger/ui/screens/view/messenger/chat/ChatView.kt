package ru.binnyatoff.messenger.ui.screens.view.messenger.chat

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import ru.binnyatoff.messenger.R
import ru.binnyatoff.messenger.data.network.models.response.MessageDTO
import ru.binnyatoff.messenger.ui.screens.view.login.components.DTextField
import ru.binnyatoff.messenger.ui.screens.view.messenger.components.Message
import ru.binnyatoff.messenger.ui.theme.AppTheme

@Composable
fun ChatView(dialogKey: String, viewModel: ChatViewModel) {
    val state = viewModel.viewState.observeAsState()
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.SpaceBetween) {
        with(state.value) {
            if (this != null) {
                if (list.isNotEmpty()) {
                    Log.e("TAG", "Loaded")
                    MessagesLoaded(list = list, user = user, modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F))
                } else {
                    Column(Modifier
                        .weight(1F)
                        .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        Image(painter = painterResource(id = R.drawable.ic_ufo),
                            contentDescription = "Empty")
                        Text(text = "Empty")
                    }
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                ) {
                    DTextField(modifier = Modifier.weight(1F),
                        value = messageField,
                        onValueChanged = { viewModel.obtainEvent(ChatEvent.MessageField(it)) },
                        label = "Message",
                        secureText = false
                    )
                    Button(onClick = { viewModel.obtainAction(ChatAction.SendMessage(dialogKey)) }) {
                        Image(painter = painterResource(id = R.drawable.ic_baseline_send),
                            contentDescription = "Send")
                        Text(text = "Send")
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        viewModel.obtainEvent(ChatEvent.ScreenInit(dialogKey = dialogKey))
    })
}

@Composable
fun MessagesLoaded(
    list: List<MessageDTO>,
    user: String,
    modifier: Modifier,
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(state = lazyListState, modifier = modifier, content = {
        list.forEach {
            if (it.message_owner == user) {
                item {
                    Message(
                        text = it.message,
                        backgroundColor = AppTheme.colors.actionTextColor,
                        horizontalArrangement = Arrangement.End,
                    )
                }
            } else {
                item {
                    Message(
                        text = it.message,
                        backgroundColor = AppTheme.colors.primaryTintColor,
                        horizontalArrangement = Arrangement.Start,
                    )
                }

            }
        }
    })

    LaunchedEffect(key1 = list, block = {
        lazyListState.animateScrollToItem(list.size)
    })
}
