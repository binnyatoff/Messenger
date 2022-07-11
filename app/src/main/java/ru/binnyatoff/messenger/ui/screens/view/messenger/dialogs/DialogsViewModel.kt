package ru.binnyatoff.messenger.ui.screens.view.messenger.dialogs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.binnyatoff.messenger.common.EventHandler
import ru.binnyatoff.messenger.data.network.Api
import ru.binnyatoff.messenger.data.network.models.response.Dialog
import javax.inject.Inject

sealed class DialogsState {
    object Loading : DialogsState()
    class Loaded(val dialogList: List<Dialog>) : DialogsState()
    object Empty : DialogsState()
    class Error(e: String) : DialogsState()
}

sealed class DialogEvent {
    object ScreenInit : DialogEvent()
}

@HiltViewModel
class DialogsViewModel @Inject constructor(val api: Api) : ViewModel(), EventHandler<DialogEvent> {

    private val _viewState = MutableLiveData<DialogsState>()
    val viewState: LiveData<DialogsState> = _viewState

    override fun obtainEvent(event: DialogEvent) {
        when (event) {
            DialogEvent.ScreenInit -> getDialogs()
        }
    }

    private fun getDialogs() {
        viewModelScope.launch {
            try {
                val response = api.getDialogs("TEST")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _viewState.postValue(DialogsState.Loaded(body))
                    } else {
                        _viewState.postValue(DialogsState.Empty)
                    }
                }
            } catch (e: Exception) {
                _viewState.postValue(DialogsState.Error(e.toString()))
            }
        }
    }

}