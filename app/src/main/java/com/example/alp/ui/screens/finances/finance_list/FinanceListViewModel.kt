package com.example.alp.ui.screens.finances.finance_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp.abstraction.Resource
import com.example.alp.core.model.FinanceModel
import com.example.alp.core.repository.contract.FinanceRepository
import com.example.alp.core.source.remote.response.BaseApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinanceListViewModel @Inject constructor(
    private val financeRepository: FinanceRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(FinanceListUiState())
    val uiState: StateFlow<FinanceListUiState> = _uiState.asStateFlow()


    fun onEvent(event: FinanceListEvent){
        when(event){
            is FinanceListEvent.Refresh -> getFinance()
        }
    }

    private fun getFinance() {
        viewModelScope.launch{
            financeRepository.finances().flowOn(Dispatchers.IO).collect{
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

    private fun handleFinanceSuccess(result: Resource.Success<BaseApiResponse<List<FinanceModel>, String>>) {
        result.data.data?.let { response ->
            _uiState.value = _uiState.value.copy(
                finances = response
            )
        }
    }

    private fun handleFinanceError(result: Resource.Error<BaseApiResponse<List<FinanceModel>, String>>) {
        _uiState.value = _uiState.value.copy(
            finances = listOf()
        )
    }

    init {
        getFinance()
    }
}