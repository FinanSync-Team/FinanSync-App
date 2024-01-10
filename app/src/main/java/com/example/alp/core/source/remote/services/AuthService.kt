package com.example.alp.core.source.remote.services

import com.example.alp.core.model.UserModel
import com.example.alp.core.source.remote.request.auth.LoginRequest
import com.example.alp.core.source.remote.request.auth.RegisterRequest
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.response.auth.login.LoginErrors
import com.example.alp.core.source.remote.response.auth.login.LoginResponse
import com.example.alp.core.source.remote.response.auth.RegisterErrors
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthService {
    @POST("auth/login")
    @Headers("No-Authentication: true")
    suspend fun login(@Body request: LoginRequest): BaseApiResponse<LoginResponse, LoginErrors>

    @POST("auth/register")
    @Headers("No-Authentication: true")
    suspend fun register(@Body request: RegisterRequest): BaseApiResponse<UserModel, RegisterErrors>

    @DELETE("auth/logout")
    suspend fun logout(): BaseApiResponse<Any, Any>
}