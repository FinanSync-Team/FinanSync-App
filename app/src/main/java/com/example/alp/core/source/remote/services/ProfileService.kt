package com.example.alp.core.source.remote.services

import com.example.alp.core.model.UserModel
import com.example.alp.core.source.remote.response.BaseApiResponse
import retrofit2.http.GET

interface ProfileService {
    @GET("profile")
    suspend fun profile(): BaseApiResponse<UserModel, String>
}