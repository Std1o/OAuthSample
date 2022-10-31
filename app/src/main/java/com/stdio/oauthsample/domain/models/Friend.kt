package com.stdio.oauthsample.domain.models

import com.google.gson.annotations.SerializedName

data class Friend(
    val id: Int,
    @SerializedName("first_name") val firstName: String,
    @SerializedName("last_name") val lastName: String,
)