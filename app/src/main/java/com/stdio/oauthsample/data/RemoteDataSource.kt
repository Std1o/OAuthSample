package com.stdio.oauthsample.data

import retrofit2.http.Query

class RemoteDataSource(private val mainService: MainService) {

    suspend fun getAccessToken(
        clientId: Int,
        clientSecret: String,
        redirectUrl: String,
        code: String
    ) = mainService.getAccessToken(clientId, clientSecret, redirectUrl, code)

    suspend fun getFriends(fields: String, version: Double) =
        mainService.getFriends(fields, version)
}