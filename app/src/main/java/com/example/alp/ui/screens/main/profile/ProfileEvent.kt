package com.example.alp.ui.screens.main.profile

sealed class ProfileEvent {
    object Refresh : ProfileEvent()
    object Logout : ProfileEvent()
}