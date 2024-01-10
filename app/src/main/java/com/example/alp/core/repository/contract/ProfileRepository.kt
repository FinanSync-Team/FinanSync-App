package com.example.alp.core.repository.contract

import com.example.alp.abstraction.Resource
import com.example.alp.core.model.UserModel
import com.example.alp.core.source.remote.response.BaseApiResponse
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    suspend fun getProfile(): Flow<Resource<BaseApiResponse<UserModel, String>>>
}