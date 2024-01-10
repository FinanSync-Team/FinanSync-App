package com.example.alp.core.source.remote.response.auth.login

import com.example.alp.core.model.UserModel
import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("user")
    val user: UserModel,
    @SerializedName("token")
    val token: String
)