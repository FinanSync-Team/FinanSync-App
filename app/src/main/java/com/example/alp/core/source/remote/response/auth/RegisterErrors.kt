package com.example.alp.core.source.remote.response.auth

import com.google.gson.annotations.SerializedName

data class RegisterErrors(
    val username: List<String> = listOf(),
    val name: List<String> = listOf(),
    val email: List<String> = listOf(),
    @SerializedName("phone_number")
    val phoneNumber: List<String> = listOf(),
    val password: List<String> = listOf(),
    @SerializedName("password_confirmation")
    val passwordConfirmation: List<String> = listOf()
)