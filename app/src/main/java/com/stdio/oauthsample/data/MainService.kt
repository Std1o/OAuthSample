package com.stdio.oauthsample.data

import com.stdio.oauthsample.domain.models.Token
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface MainService {

    @GET("access_token")
    suspend fun getAccessToken(
        @Query("client_id") clientId: Int,
        @Query("client_secret") clientSecret: String,
        @Query("redirect_uri") redirectUrl: String,
        @Query("code") code: String
    ): Response<Token>
}