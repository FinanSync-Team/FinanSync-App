package com.example.alp.core.source.remote

import com.example.alp.abstraction.HttpExceptionError
import com.example.alp.abstraction.HttpResult
import com.example.alp.abstraction.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

open class RemoteDataSource {
    suspend inline fun <reified T> safeApiCall(
        crossinline apiCall: suspend () -> T
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                val result = apiCall.invoke()
                Resource.Success(result)
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> HttpExceptionError().parse(throwable)
                    is UnknownHostException -> Resource.Error(
                        message = "No internet connection",
                        cause = HttpResult.NO_CONNECTION
                    )
                    is SocketTimeoutException -> Resource.Error(
                        message = "Opps... Timeout Connection!",
                        cause = HttpResult.TIMEOUT
                    )
                    is IOException -> Resource.Error(
                        message = throwable.message.toString(),
                        cause = HttpResult.BAD_RESPONSE
                    )
                    else -> {
                        Resource.Error(
                            message = throwable.message.toString(),
                            cause = HttpResult.NOT_DEFINED
                        )
                    }
                }
            }
        }
    }
}