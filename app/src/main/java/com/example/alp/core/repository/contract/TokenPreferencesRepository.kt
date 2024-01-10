package com.example.alp.core.repository.contract

import kotlinx.coroutines.flow.Flow

interface TokenPreferencesRepository {
    suspend fun getAccessToken(): Flow<String>
    suspend fun setAccessToken(token: String)
    suspend fun clearToken()

    suspend fun getName(): Flow<String>
    suspend fun setName(name: String)
    suspend fun clearName()

    suspend fun setEmail(email: String)
    suspend fun getEmail(): Flow<String>
    suspend fun clearEmail()
}