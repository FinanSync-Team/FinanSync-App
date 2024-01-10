package com.example.alp.core.source.remote.response.auth.login

data class LoginErrors(
    val login: List<String> = listOf(),
    val password: List<String> = listOf()
)