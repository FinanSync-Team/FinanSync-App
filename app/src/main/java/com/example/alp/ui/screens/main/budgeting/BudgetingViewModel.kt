package com.example.alp.ui.screens.main.budgeting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp.abstraction.Resource
import com.example.alp.core.repository.contract.FinanceRepository
import com.example.alp.core.repository.contract.TokenPreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BudgetingViewModel @Inject constructor(
    private val financeRepository: FinanceRepository,
    private val tokenPreferencesRepository: TokenPreferencesRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BudgetingUiState())
    val uiState: StateFlow<BudgetingUiState> = _uiState.asStateFlow()

    fun onEvent(event: BudgetingEvent) {
        when (event) {
            BudgetingEvent.Refresh -> {
                getBudgeting()
            }
        }
    }

    private fun getBudgeting() {
        viewModelScope.launch {
            financeRepository.budgeting().collect() { result ->
                _uiState.value = _uiState.value.copy(resource = result)
                when (result) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {
                        result.data.data?.let { response ->
                            _uiState.value = _uiState.value.copy(
                                progress = response.progress,
                                monthlyBudgeting = response.monthlyBudgeting,
                                chart = response.chart
                            )
                        }

                    }
                }
            }
        }
    }


    init {
        getBudgeting()
        getLocalData()
    }

    private fun getLocalData() {
        viewModelScope.launch {
            tokenPreferencesRepository.getName().collect { result ->
                _uiState.value = _uiState.value.copy(name = result)
            }
            tokenPreferencesRepository.getEmail().collect { result ->
                _uiState.value = _uiState.value.copy(email = result)
            }
        }
    }
}