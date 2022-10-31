package com.stdio.oauthsample.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.stdio.oauthsample.common.AccountSession
import com.stdio.oauthsample.data.MainRepository
import com.stdio.oauthsample.domain.DataState
import com.stdio.oauthsample.domain.models.BaseListResponse
import com.stdio.oauthsample.domain.models.BaseResponse
import com.stdio.oauthsample.domain.models.Friend
import com.stdio.oauthsample.domain.models.Token
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) :
    BaseViewModel<BaseResponse<Friend>>() {

    private val _tokenState = MutableStateFlow<DataState<Token>>(DataState.Initial)
    val tokenState: StateFlow<DataState<Token>> = _tokenState.asStateFlow()

    fun getFriends() {
        viewModelScope.launch {
            launchRequest(repository.getFriends("nickname", 5.131))
        }
    }

    fun getAccessToken(code: String) {
        viewModelScope.launch {
            repository.getAccessToken(
                51463205,
                "pruWBv4n6ondPgVrRaVG",
                "https://oauth.vk.com/blank.html",
                code
            ).collect {
                if (it is DataState.Success) {
                    AccountSession.instance.token = it.data.token
                    AccountSession.instance.userId = it.data.userId
                }
                _tokenState.value = it
                getFriends()
            }
        }
    }
}