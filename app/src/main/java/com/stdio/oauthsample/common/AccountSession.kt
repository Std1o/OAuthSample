package com.stdio.oauthsample.common


class AccountSession {
    var token: String? = null
    var userId: Int? = null


    companion object {
        var instance: AccountSession = AccountSession()
    }
}