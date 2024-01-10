package com.example.alp.ui.screens.auth.register

sealed class RegisterEvent {
    object OnRegisterClicked: RegisterEvent()
}