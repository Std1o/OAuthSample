package com.stdio.oauthsample.data

import com.stdio.oauthsample.domain.DataState
import retrofit2.Response
import com.stdio.oauthsample.common.Utils.encodeErrorCode

open class BaseRepository {

    suspend fun <T> apiCall(call: suspend () -> Response<T>): DataState<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return DataState.Success(body)
                }
                return DataState.Empty(response.code())
            }
            val errorMessage = encodeErrorCode(response.errorBody())
            return error(errorMessage, response.code())
        } catch (e: Exception) {
            return error(e.message ?: " ", -1)
        }
    }

    private fun <T> error(errorMessage: String, code: Int): DataState<T> =
        DataState.Error(errorMessage, code)
}