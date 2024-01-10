package com.example.alp.ui.screens.main.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp.abstraction.Resource
import com.example.alp.core.repository.contract.FinanceRepository
import com.example.alp.core.repository.contract.TokenPreferencesRepository
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.response.finance.HomeResponse
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
class HomeViewModel @Inject constructor(
    private val financeRepository: FinanceRepository,
    private val tokenPreferencesRepository: TokenPreferencesRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onEvent(event: HomeEvent){
        when(event){
            is HomeEvent.Refresh -> getFinance()
        }
    }


    private fun getFinance() {
        viewModelScope.launch{
            financeRepository.home().flowOn(Dispatchers.IO).collect{
                result ->
                _uiState.value = _uiState.value.copy(resource = result)
                when (result) {
                    is Resource.Error -> handleFinanceError(result)
                    is Resource.Success -> viewModelScope.launch {
                        handleFinanceSuccess(result)
                    }
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun handleFinanceSuccess(result: Resource.Success<BaseApiResponse<HomeResponse, String>>) {
        result.data.data?.let { response ->
            _uiState.value = _uiState.value.copy(
                name = response.user.name,
                email = response.user.email,
                balance = response.balance.formattedValue,
                income = response.budget.formattedIncome,
                expense = response.budget.formattedExpense,
                finances = response.finances
            )
            viewModelScope.launch {
                tokenPreferencesRepository.setEmail(response.user.email)
                tokenPreferencesRepository.setName(response.user.name)
            }
        }

    }

    private fun handleFinanceError(result: Resource.Error<BaseApiResponse<HomeResponse, String>>) {
    }

    private fun getLocalProfile() {
        viewModelScope.launch{
            tokenPreferencesRepository.getName().flowOn(Dispatchers.IO).collect{
                    result ->
                _uiState.value = _uiState.value.copy(name = result)
            }

            tokenPreferencesRepository.getEmail().flowOn(Dispatchers.IO).collect{
                    result ->
                _uiState.value = _uiState.value.copy(email = result)
            }
        }
    }

    init {
        getLocalProfile()
        getFinance()
    }


}