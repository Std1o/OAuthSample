package com.stdio.oauthsample.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import com.stdio.oauthsample.data.MainRepository
import com.stdio.oauthsample.domain.models.Token
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : BaseViewModel<Token>() {

    fun getAccessToken(code: String) {
        viewModelScope.launch {
            launchRequest(
                repository.getAccessToken(
                    51463205,
                    "pruWBv4n6ondPgVrRaVG",
                    "https://oauth.vk.com/blank.html",
                    code
                )
            )
        }
    }
}