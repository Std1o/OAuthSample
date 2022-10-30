package com.stdio.oauthsample.data

import kotlinx.coroutines.flow.flow

class MainRepository(private val remoteDataSource: RemoteDataSource) : BaseRepository() {

    suspend fun getAccessToken(
        clientId: Int,
        clientSecret: String,
        redirectUrl: String,
        code: String
    ) =
        flow {
            emit(apiCall {
                remoteDataSource.getAccessToken(
                    clientId,
                    clientSecret,
                    redirectUrl,
                    code
                )
            })
        }
}