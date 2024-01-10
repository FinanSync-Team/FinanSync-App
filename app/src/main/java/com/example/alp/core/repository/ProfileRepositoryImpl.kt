package com.example.alp.core.repository

import com.example.alp.abstraction.Resource
import com.example.alp.core.model.UserModel
import com.example.alp.core.repository.contract.ProfileRepository
import com.example.alp.core.source.remote.RemoteDataSource
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.services.ProfileService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ProfileRepositoryImpl @Inject constructor(
    private val api: ProfileService
) : ProfileRepository, RemoteDataSource() {
    override suspend fun getProfile(): Flow<Resource<BaseApiResponse<UserModel, String>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.profile()
        }
        emit(response)
    }
}