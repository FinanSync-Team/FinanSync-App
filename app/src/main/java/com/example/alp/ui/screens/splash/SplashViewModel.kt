package com.example.alp.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp.core.repository.contract.TokenPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenPreferencesRepository: TokenPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(SplashUiState())
    val uiState: StateFlow<SplashUiState> = _uiState.asStateFlow()
    private fun checkIsAuthenticated() {
        viewModelScope.launch{
            val isAuthenticated = runBlocking {
                return@runBlocking tokenPreferencesRepository.getAccessToken().first()
            }
            _uiState.value = _uiState.value.copy(isAuthenticated = isAuthenticated.isNotEmpty())
        }
    }

    init {
        checkIsAuthenticated()
    }
}