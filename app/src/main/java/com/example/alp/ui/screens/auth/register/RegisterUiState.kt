package com.example.alp.ui.screens.auth.register

import com.example.alp.abstraction.Resource
import com.example.alp.ui.components.TextFieldState

data class RegisterUiState(
    val resource: Resource<Any>? = null,
    val name: TextFieldState = TextFieldState("required|min:3|max:255"),
    val username: TextFieldState = TextFieldState("required|min:3|max:255"),
    val email: TextFieldState = TextFieldState("required|email"),
    val phoneNumber: TextFieldState = TextFieldState("required|min:5|max:13|numeric"),
    val password: TextFieldState = TextFieldState("required|min:6"),
    val successRegister: Boolean = false
)