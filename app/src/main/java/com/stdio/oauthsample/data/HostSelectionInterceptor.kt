package com.stdio.oauthsample.data

import com.stdio.oauthsample.common.AccountSession
import okhttp3.*
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import java.io.IOException


/** An interceptor that allows runtime changes to the URL hostname.  */
class HostSelectionInterceptor : Interceptor {

    private val authUrl: String by inject(
        qualifier = named("auth_url"),
        clazz = String::class.java
    )
    private val baseUrl: String by inject(
        qualifier = named("base_url"),
        clazz = String::class.java
    )

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        if (!AccountSession.instance.token.isNullOrEmpty()) {
            val oldUrl = request.url().url().toString()
            println(oldUrl)
            val newUrl = HttpUrl.parse(oldUrl.replace(authUrl, baseUrl))
            println(newUrl)
            request = request.newBuilder()
                .url(newUrl)
                .build()
        }
        return chain.proceed(request)
    }
}