package com.example.alp.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp.abstraction.Resource
import com.example.alp.core.repository.contract.AuthRepository
import com.example.alp.core.repository.contract.TokenPreferencesRepository
import com.example.alp.core.source.remote.request.auth.LoginRequest
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.response.auth.login.LoginErrors
import com.example.alp.core.source.remote.response.auth.login.LoginResponse
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
class LoginViewModel @Inject constructor(
    private val tokenPreferencesRepository: TokenPreferencesRepository,
    private val authRepository: AuthRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            LoginEvent.OnLoginClicked -> {
                val isLoginValid = _uiState.value.login.validate()
                val isPasswordValid = _uiState.value.password.validate()
                if (isLoginValid && isPasswordValid) {
                    login()
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch{
            val request = LoginRequest(
                login = _uiState.value.login.text,
                password = _uiState.value.password.text
            )
            authRepository.login(request).flowOn(Dispatchers.IO).collect{
                result ->
                _uiState.value = _uiState.value.copy(resource = result)
                when (result) {
                    is Resource.Error -> handleLoginError(result)
                    is Resource.Success -> viewModelScope.launch {
                        handleLoginSuccess(result)
                    }
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private suspend fun handleLoginSuccess(result: Resource.Success<BaseApiResponse<LoginResponse, LoginErrors>>) {
        val response = result.data.data
        response?.let { res ->
            tokenPreferencesRepository.setAccessToken(res.token)
            tokenPreferencesRepository.setName(res.user.name)
            tokenPreferencesRepository.setEmail(res.user.email)
            _uiState.value = _uiState.value.copy(isSuccessLogin = true)
        }
    }

    private fun handleLoginError(result: Resource.Error<BaseApiResponse<LoginResponse, LoginErrors>>) {
        val error = result.data?.errors
        error?.let {
            _uiState.value.login.serverValidation(it.login)
            _uiState.value.password.serverValidation(it.password)
        }
    }
}