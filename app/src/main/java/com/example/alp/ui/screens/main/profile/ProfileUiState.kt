package com.example.alp.ui.screens.main.profile

import com.example.alp.abstraction.Resource

data class ProfileUiState(
    val resource: Resource<Any>? = null,
    val name: String = "",
    val username: String = "",
    val returnFromLogout: Boolean = false
)