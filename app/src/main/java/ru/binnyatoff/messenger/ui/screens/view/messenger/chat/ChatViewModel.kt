package ru.binnyatoff.messenger.ui.screens.view.messenger.chat

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.binnyatoff.messenger.common.EventHandler
import ru.binnyatoff.messenger.data.network.Api
import ru.binnyatoff.messenger.data.network.models.response.MessageDTO
import javax.inject.Inject

data class ChatState(
    val list: List<MessageDTO> = listOf(),
    val messageField: String = "",
    val isLoaded: Boolean = false,
    val error: String = "",
    val empty: Boolean = true,
    val user: String = "TEST",
)

sealed class ChatEvent() {
    data class MessageField(val value: String) : ChatEvent()
    data class ScreenInit(val dialogKey: String) : ChatEvent()
}

sealed class ChatAction() {
    data class SendMessage(val dialogKey: String) : ChatAction()
    object None : ChatAction()
}

@HiltViewModel
class ChatViewModel @Inject constructor(private val api: Api) : ViewModel(),
    EventHandler<ChatEvent> {
    private val _viewState = MutableLiveData(ChatState())
    private var dialogKey = ""
    val viewState: LiveData<ChatState> = _viewState

    override fun obtainEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.MessageField -> messageChanged(event.value)
            is ChatEvent.ScreenInit -> {
                dialogKey = event.dialogKey
                refreshMessage(dialogKey)
            }
        }
    }

    fun obtainAction(action: ChatAction) {
        when (action) {
            is ChatAction.SendMessage -> sendMessage(action.dialogKey)
        }
    }

    private fun sendMessage(dialogKey: String) {
        val message = _viewState.value?.messageField
        if (message != null) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val messageobj = MessageDTO(
                        dialog_key = dialogKey,
                        message_owner = "TEST",
                        message = message
                    )
                    val response = api.sendMessage(messageobj)
                    if (response.isSuccessful) {
                        getMessages(dialogKey)
                        _viewState.postValue(_viewState.value?.copy(messageField = ""))
                    }
                } catch (e: Exception) {
                    Log.e("TAG", "$e")
                }
            }
        }
    }

    private fun messageChanged(value: String) {
        _viewState.postValue(_viewState.value?.copy(messageField = value))
    }


    private fun getMessages(dialogKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = api.getMessages(dialogKey)
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        if (body.isNotEmpty()) {
                            Log.e("TAG", "Not Null")
                            _viewState.postValue(_viewState.value?.copy(list = body,
                                isLoaded = true,
                                empty = false))
                        } else {
                            Log.e("TAG", "Empty")
                        }

                    } else {
                        Log.e("TAG", "Null")
                        _viewState.postValue(_viewState.value?.copy(list = emptyList(),
                            isLoaded = true,
                            empty = true))
                    }
                }
            } catch (e: Exception) {
                Log.e("TAG", "$e")
                _viewState.postValue(_viewState.value?.copy(list = emptyList(),
                    isLoaded = false,
                    empty = false,
                    error = e.toString()))
            }
        }
    }

    private fun refreshMessage(dialogKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getMessages(dialogKey)
            delay(5000)
            refreshMessage(dialogKey)
        }
    }
}