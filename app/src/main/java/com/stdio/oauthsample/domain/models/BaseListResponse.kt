package com.stdio.oauthsample.domain.models

data class BaseListResponse<T>(val count: Int, val items: List<T>)
