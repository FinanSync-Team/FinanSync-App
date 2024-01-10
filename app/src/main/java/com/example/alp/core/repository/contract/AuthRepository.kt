package com.example.alp.core.repository.contract

import com.example.alp.abstraction.Resource
import com.example.alp.core.model.UserModel
import com.example.alp.core.source.remote.request.auth.LoginRequest
import com.example.alp.core.source.remote.request.auth.RegisterRequest
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.response.auth.RegisterErrors
import com.example.alp.core.source.remote.response.auth.login.LoginErrors
import com.example.alp.core.source.remote.response.auth.login.LoginResponse
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun register(request: RegisterRequest): Flow<Resource<BaseApiResponse<UserModel, RegisterErrors>>>
    suspend fun login(request: LoginRequest): Flow<Resource<BaseApiResponse<LoginResponse, LoginErrors>>>
    suspend fun logout(): Flow<Resource<BaseApiResponse<Any, Any>>>
}