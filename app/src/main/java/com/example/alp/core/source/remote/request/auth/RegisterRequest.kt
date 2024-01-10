package com.example.alp.core.source.remote.request.auth

import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    val username: String,
    val name: String,
    val email: String,
    @SerializedName("phone_number")
    val phoneNumber: String,
    val password: String,
    @SerializedName("password_confirmation")
    val passwordConfirmation: String,
)
