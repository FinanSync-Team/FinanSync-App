package com.example.alp.abstraction

sealed class Resource<out T> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error<out T>(
        val data: T? = null,
        val message: String = "Unknown error",
        val code: Int? = null,
        val cause: HttpResult = HttpResult.NOT_DEFINED
    ) : Resource<T>()

    data class Loading<out T>(val data: T? = null) : Resource<T>()
}