package com.example.alp.ui.screens.finances.monthly_budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp.abstraction.Resource
import com.example.alp.core.repository.contract.FinanceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MonthlyBudgetViewModel @Inject constructor(
    private val financeRepository: FinanceRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(MonthlyBudgetUiState())
    val uiState: StateFlow<MonthlyBudgetUiState> = _uiState.asStateFlow()


    fun onEvent(event: MonthlyBudgetEvent) {
        when (event) {
            MonthlyBudgetEvent.Submit ->  {
                if(_uiState.value.budget.validate()) setMonthlyBudget()
            }
        }
    }

    private fun setMonthlyBudget() {
        viewModelScope.launch {
            financeRepository.setMonthlyBudget(_uiState.value.budget.text.toInt()).flowOn(Dispatchers.IO).collect{ result ->
                _uiState.value = _uiState.value.copy(resource = result)
                when (result) {
                    is Resource.Error -> {}
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(returnFromSubmit = true)
                    }
                    is Resource.Loading -> {}
                }
            }
        }
    }
}