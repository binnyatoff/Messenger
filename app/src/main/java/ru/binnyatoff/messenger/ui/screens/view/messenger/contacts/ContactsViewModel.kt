package ru.binnyatoff.messenger.ui.screens.view.messenger.contacts

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.binnyatoff.messenger.common.EventHandler
import ru.binnyatoff.messenger.data.network.Api
import ru.binnyatoff.messenger.data.network.models.query.DialogWithOutKey
import ru.binnyatoff.messenger.data.network.models.response.ListUsers
import javax.inject.Inject

sealed class ContactsState() {
    object Loading : ContactsState()
    class Loaded(val listUsers: ListUsers) : ContactsState()
    object Empty : ContactsState()
    class Error(val error: String) : ContactsState()
}

sealed class ContactsEvent() {
    object ScreenInit : ContactsEvent()
    class ContactClicked(val contactLogin: String) : ContactsEvent()
}

@HiltViewModel
class ContactsViewModel @Inject constructor(val api: Api) : ViewModel(),
    EventHandler<ContactsEvent> {


    private val _viewState = MutableLiveData<ContactsState>(ContactsState.Loading)
    val viewState: LiveData<ContactsState> = _viewState


    override fun obtainEvent(event: ContactsEvent) {
        when (event) {
            ContactsEvent.ScreenInit -> getListUsers()
           is ContactsEvent.ContactClicked -> addDialog(event.contactLogin)
        }
    }

    private fun addDialog(contactLogin: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
              api.addDialog(DialogWithOutKey(
                    user_one = "TEST",
                    user_two = contactLogin
                ))
            } catch (e: Exception) {

            }
        }
    }

    private fun getListUsers() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = api.getListUsers("TEST") //Provide me Login please
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _viewState.postValue(ContactsState.Loaded(listUsers = body))
                    } else {
                        _viewState.postValue(ContactsState.Empty)
                    }
                } else {
                    _viewState.postValue(ContactsState.Error(response.message()))
                }
            } catch (e: Exception) {
                _viewState.postValue(ContactsState.Error(e.toString()))
            }
        }
    }
}