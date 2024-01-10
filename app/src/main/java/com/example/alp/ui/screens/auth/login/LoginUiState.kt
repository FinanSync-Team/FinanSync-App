package com.example.alp.ui.screens.auth.login

import com.example.alp.abstraction.Resource
import com.example.alp.ui.components.TextFieldState

data class LoginUiState(
    val resource: Resource<Any>? = null,
    val login: TextFieldState = TextFieldState("required"),
    val password: TextFieldState = TextFieldState("required"),
    val isSuccessLogin: Boolean = false
)
