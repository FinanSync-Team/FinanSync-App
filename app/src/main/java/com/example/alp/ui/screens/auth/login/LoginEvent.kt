package com.example.alp.ui.screens.auth.login

sealed class  LoginEvent {
    object OnLoginClicked: LoginEvent()
}