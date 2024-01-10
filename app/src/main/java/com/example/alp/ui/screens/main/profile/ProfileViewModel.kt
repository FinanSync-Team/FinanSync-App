package com.example.alp.ui.screens.main.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp.abstraction.Resource
import com.example.alp.core.repository.contract.AuthRepository
import com.example.alp.core.repository.contract.ProfileRepository
import com.example.alp.core.repository.contract.TokenPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val authRepository: AuthRepository,
    private val tokenPreferencesRepository: TokenPreferencesRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.Logout -> logout()
            ProfileEvent.Refresh -> getProfile()
        }
    }

    init {
        getProfile()
    }

    private fun getProfile() {
        viewModelScope.launch {
            profileRepository.getProfile().flowOn(Dispatchers.IO).collect{result ->
                _uiState.value = _uiState.value.copy(resource = result)
                when (result) {
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        if(result.data.data != null) {
                            _uiState.value = _uiState.value.copy(
                                name = result.data.data.name,
                                username = result.data.data.username
                            )
                        }
                    }
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun logout(){
        viewModelScope.launch {
            authRepository.logout().flowOn(Dispatchers.IO).collect{result ->
                _uiState.value = _uiState.value.copy(resource = result)
                when (result) {
                    is Resource.Error -> {}
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                }
                _uiState.value = _uiState.value.copy(returnFromLogout = true)
                tokenPreferencesRepository.clearToken()
                tokenPreferencesRepository.clearEmail()
                tokenPreferencesRepository.clearName()
            }
        }
    }
}