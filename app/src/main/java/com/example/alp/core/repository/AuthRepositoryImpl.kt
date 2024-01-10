package com.example.alp.core.repository

import com.example.alp.abstraction.Resource
import com.example.alp.core.model.UserModel
import com.example.alp.core.repository.contract.AuthRepository
import com.example.alp.core.source.remote.RemoteDataSource
import com.example.alp.core.source.remote.request.auth.LoginRequest
import com.example.alp.core.source.remote.request.auth.RegisterRequest
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.response.auth.RegisterErrors
import com.example.alp.core.source.remote.response.auth.login.LoginErrors
import com.example.alp.core.source.remote.response.auth.login.LoginResponse
import com.example.alp.core.source.remote.services.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val api: AuthService
): AuthRepository, RemoteDataSource() {
    override suspend fun register(request: RegisterRequest): Flow<Resource<BaseApiResponse<UserModel, RegisterErrors>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.register(request)
        }
        emit(response)
    }.flowOn(Dispatchers.IO)

    override suspend fun login(request: LoginRequest): Flow<Resource<BaseApiResponse<LoginResponse, LoginErrors>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.login(request)
        }
        emit(response)
    }.flowOn(Dispatchers.IO)

    override suspend fun logout(): Flow<Resource<BaseApiResponse<Any, Any>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.logout()
        }
        emit(response)
    }.flowOn(Dispatchers.IO)
}