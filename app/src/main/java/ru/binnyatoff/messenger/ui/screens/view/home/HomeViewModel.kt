package ru.binnyatoff.messenger.ui.screens.view.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.binnyatoff.messenger.data.network.Api
import ru.binnyatoff.messenger.ui.screens.view.home.models.HomeViewState
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val api: Api) : ViewModel() {

    private val _viewState = MutableLiveData<HomeViewState>(HomeViewState.Loading)
    val viewState: LiveData<HomeViewState> = _viewState


    fun getListUsers() {
        _viewState.postValue(HomeViewState.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = api.getListUsers("TEST")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _viewState.postValue(HomeViewState.Loaded(body))
                    }
                } else {
                    Log.e("TAG", response.toString())
                    Log.e("TAG", response.body().toString())
                }
            } catch (e: Exception) {
                Log.e("TAG", "$e")
            }
        }
    }
}