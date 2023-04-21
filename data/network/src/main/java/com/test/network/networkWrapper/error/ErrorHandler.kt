package com.test.network.networkWrapper.error

import com.test.common_jvm.ErrorEntity
import com.test.common_jvm.ResultWrapper
import com.test.network.networkWrapper.RetryPolicy
import retrofit2.Response

interface ErrorHandler {

    suspend fun <ResultType> getSafe(
        retryPolicy: RetryPolicy = RetryPolicy(),
        remoteFetch: suspend () -> Response<ResultType>
    ): ResultWrapper<ResultType>

    fun getError(throwable: Throwable): ErrorEntity
}