package com.example.alp.abstraction

import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.utils.baseApiResponseType
import com.example.alp.utils.fromJson
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.HttpException
import java.lang.reflect.Type

class HttpExceptionError {
    inline fun <reified T> parse(throwable: HttpException): Resource<T> {
        return when (throwable.code()) {
            in 400..451 -> {
                val resource = try {
                    val errorBody = throwable.response()?.errorBody()?.charStream()?.readText()
                        ?: "Unknown HTTP error body"
                    val typeOfT = object : TypeToken<T>() {}.type
                    val errorResponse: T? = parseErrorBody(errorBody, typeOfT)

                    val message = if (errorResponse is BaseApiResponse<*, *>) {
                        errorResponse.message
                    } else {
                        throwable.message() ?: "Unknown error"
                    }
                    Resource.Error(
                        data = errorResponse,
                        message = message,
                        code = throwable.code(),
                        cause = HttpResult.CLIENT_ERROR
                    )
                } catch (e: Exception) {
                    Resource.Error(
                        data = null,
                        message = e.message.toString(),
                        code = throwable.code(),
                        cause = HttpResult.CLIENT_ERROR
                    )
                }
                resource
            }
            in 500..599 -> Resource.Error(
                data = null,
                message = "The server is having problems",
                code = throwable.code(),
                cause = HttpResult.SERVER_ERROR
            )

            else -> Resource.Error(
                data = null,
                message = "Undefined error",
                code = throwable.code(),
                cause = HttpResult.NOT_DEFINED
            )
        }
    }

    fun <T> parseErrorBody(errorBody: String, typeOfT: Type): T? {
        return try {
            Gson().fromJson<T>(errorBody, typeOfT)
        } catch (e: Exception) {
            null
        }
    }
}