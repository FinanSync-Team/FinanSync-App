package com.example.alp.core.repository

import com.example.alp.core.repository.contract.TokenPreferencesRepository
import com.example.alp.core.source.local.TokenPreferences
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TokenPreferencesRepositoryImpl @Inject constructor(
    private val pref: TokenPreferences
): TokenPreferencesRepository {
    override suspend fun getAccessToken(): Flow<String> {
        return pref.getAccessToken()
    }

    override suspend fun setAccessToken(token: String) {
        pref.setAccessToken(token)
    }

    override suspend fun clearToken() {
        pref.clearToken()
    }

    override suspend fun getName(): Flow<String> {
        return pref.getName()
    }

    override suspend fun setName(name: String) {
        pref.setName(name)
    }

    override suspend fun clearName() {
        pref.clearName()
    }

    override suspend fun setEmail(email: String) {
        pref.setEmail(email)
    }

    override suspend fun getEmail(): Flow<String> {
        return pref.getEmail()
    }

    override suspend fun clearEmail() {
        pref.clearEmail()
    }


}