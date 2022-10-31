package com.stdio.oauthsample.data

import com.stdio.oauthsample.common.AccountSession
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response


class OAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val token = AccountSession.instance.token
        if (!token.isNullOrEmpty()) {
            val url: HttpUrl = request
                .url()
                .newBuilder()
                .addQueryParameter("access_token", token).build()
            request = request.newBuilder().url(url).build()
        }
        return chain.proceed(request)
    }
}