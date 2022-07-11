package ru.binnyatoff.messenger.ui.screens.view.splash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import ru.binnyatoff.messenger.common.EventHandler
import ru.binnyatoff.messenger.ui.screens.MainSharedPreferences
import javax.inject.Inject

sealed class SplashState {
    object SignUpState : SplashState()
    object MainState : SplashState()
    object None : SplashState()
}

sealed class SplashEvent {
    object ScreenInit : SplashEvent()
    object OpenScreen : SplashEvent()
}

@HiltViewModel
class SplashViewModel @Inject constructor(private val mainSharedPreferences: MainSharedPreferences) :
    ViewModel(), EventHandler<SplashEvent> {

    private val _viewState = MutableLiveData<SplashState>(SplashState.None)
    val viewState: LiveData<SplashState> = _viewState

    private fun getToken() {
        if (mainSharedPreferences.getToken() != "") {
            Log.e("TAG", mainSharedPreferences.getToken().toString())
            _viewState.postValue(SplashState.MainState)
        }else{
            _viewState.postValue(SplashState.SignUpState)
        }
    }

    override fun obtainEvent(event: SplashEvent) {
        when (event) {
           // SplashEvent.OpenScreen -> _viewState.postValue(SplashState.None)
            SplashEvent.ScreenInit -> getToken()
        }
    }
}