package com.cme_mahmoud.data.model

import com.cme_mahmoud.common.Outcome
import retrofit2.Response

abstract class BaseApiResponse {

    suspend fun <T> safeApiCall(apiCall: suspend () -> Response<T>): Outcome<T> {
        try {
            val response = apiCall()
            if (response.isSuccessful) {
                val body = response.body()
                body?.let {
                    return Outcome.success(body)
                }
            }
            return Outcome.apiError(response.code(),response.message())
        } catch (e: Exception) {
            return Outcome.failure(e)
        }
    }
}