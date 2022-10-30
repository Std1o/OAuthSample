package com.stdio.oauthsample.domain.models

import com.google.gson.annotations.SerializedName

data class Token(
    @SerializedName("access_token") val token: String,
    @SerializedName("user_id") val userId: Int
)
