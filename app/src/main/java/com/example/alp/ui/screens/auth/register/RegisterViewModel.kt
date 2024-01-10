package com.example.alp.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp.abstraction.Resource
import com.example.alp.core.model.UserModel
import com.example.alp.core.repository.contract.AuthRepository
import com.example.alp.core.repository.contract.TokenPreferencesRepository
import com.example.alp.core.source.remote.request.auth.RegisterRequest
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.response.auth.RegisterErrors
import com.example.alp.ui.screens.auth.login.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onEvent(event: RegisterEvent) {
        when (event) {
            RegisterEvent.OnRegisterClicked -> {
                val isNameValid = _uiState.value.name.validate()
                val isUsernameValid = _uiState.value.username.validate()
                val isEmailValid = _uiState.value.email.validate()
                val isPhoneNumberValid = _uiState.value.phoneNumber.validate()
                val isPasswordValid = _uiState.value.password.validate()
                if (isNameValid && isUsernameValid && isEmailValid && isPhoneNumberValid && isPasswordValid) {
                    register()
                }
            }
        }
    }

    private fun register() {
        viewModelScope.launch{
            val request = RegisterRequest(
                name = _uiState.value.name.text,
                username = _uiState.value.username.text,
                email = _uiState.value.email.text,
                phoneNumber = _uiState.value.phoneNumber.text,
                password = _uiState.value.password.text,
                passwordConfirmation = _uiState.value.password.text,
            )
            authRepository.register(request).flowOn(Dispatchers.IO).collect{
                result ->
                _uiState.value = _uiState.value.copy(resource = result)
                when (result) {
                    is Resource.Error -> handleRegisterError(result)
                    is Resource.Success -> viewModelScope.launch {
                        handleRegisterSuccess(result)
                    }
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun handleRegisterSuccess(result: Resource.Success<BaseApiResponse<UserModel, RegisterErrors>>) {
        _uiState.value = _uiState.value.copy(successRegister = true)
    }

    private fun handleRegisterError(result: Resource.Error<BaseApiResponse<UserModel, RegisterErrors>>) {
        val error = result.data?.errors
        error?.let {
            _uiState.value.name.serverValidation(it.name)
            _uiState.value.username.serverValidation(it.username)
            _uiState.value.email.serverValidation(it.email)
            _uiState.value.phoneNumber.serverValidation(it.phoneNumber)
            _uiState.value.password.serverValidation(it.password)
        }
    }


}