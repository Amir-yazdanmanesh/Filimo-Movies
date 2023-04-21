package com.test.network.networkWrapper

import com.test.common_jvm.ErrorEntity
import com.test.common_jvm.NoInternetException
import com.test.common_jvm.ResultWrapper
import com.test.common_jvm.ServerConnectionException
import kotlinx.coroutines.*
import kotlinx.coroutines.sync.withLock
import com.test.network.networkWrapper.connectivity.Connectivity
import com.test.network.networkWrapper.connectivity.ConnectivityPublisher
import com.test.network.networkWrapper.error.GeneralErrorHandlerImpl
import retrofit2.Response
import java.io.IOException

abstract class NetworkWrapper : GeneralErrorHandlerImpl() {

    override suspend fun <ResultType> getSafe(
        retryPolicy: RetryPolicy,
        remoteFetch: suspend () -> Response<ResultType>
    ): ResultWrapper<ResultType> =
        withContext(Dispatchers.IO) {
            handleResponse {
                retryIO(
                    retryPolicy,
                    this
                ) { remoteFetch() }
            }
        }

    private inline fun <ResultType> handleResponse(call: () -> Response<ResultType>): ResultWrapper<ResultType> {
        return try {
            val response = call()
            if (response.isSuccessful) {
                response.body()?.let {
                    return ResultWrapper.Success(
                        data = it,
                        code = response.code()
                    )
                }
            }
            return ResultWrapper.Failed(
                error = ErrorEntity.Api(response.errorBody()?.string()),
            )
        } catch (t: Throwable) {
            ResultWrapper.Failed(getError(t))
        }
    }

    suspend fun <T> retryIO(
        retryPolicy: RetryPolicy = RetryPolicy(),
        coroutineScope: CoroutineScope?,
        block: suspend () -> T
    ): T = General.getMutex.withLock {
        if (!General.shouldRetryNetworkCall) coroutineScope?.cancel() // disable retryIo api call

        var currentDelay = retryPolicy.initialDelay
        repeat(retryPolicy.times - 1) { index ->
            try {
                return block()
            } catch (e: IOException) {
                if (index == retryPolicy.times - 2 && (e is NoInternetException || e is ServerConnectionException)) {
                    ConnectivityPublisher.notifySubscribers(Connectivity(General.DISCONNECT))
                }
            }
            if (General.shouldRetryNetworkCall) {
                delay(currentDelay)
                currentDelay =
                    (currentDelay * retryPolicy.factor).toLong().coerceAtMost(retryPolicy.maxDelay)
            }
        }
        return block()
    }

}