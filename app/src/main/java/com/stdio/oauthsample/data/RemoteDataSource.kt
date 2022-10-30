package com.stdio.oauthsample.data

class RemoteDataSource(private val mainService: MainService) {

    suspend fun getAccessToken(
        clientId: Int,
        clientSecret: String,
        redirectUrl: String,
        code: String
    ) = mainService.getAccessToken(clientId, clientSecret, redirectUrl, code)
}