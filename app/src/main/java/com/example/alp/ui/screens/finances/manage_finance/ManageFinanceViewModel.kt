package com.example.alp.ui.screens.finances.manage_finance

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alp.abstraction.Resource
import com.example.alp.core.model.FinanceModel
import com.example.alp.core.repository.contract.FinanceRepository
import com.example.alp.core.source.remote.request.finance.ManageFinanceRequest
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.response.finance.ManageFinanceErrors
import com.example.alp.ui.navigation.Params
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ManageFinanceViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val financeRepository: FinanceRepository
): ViewModel(){
    private val _uiState = MutableStateFlow(ManageFinanceUiState())
    val uiState: StateFlow<ManageFinanceUiState> = _uiState.asStateFlow()
    private var financeId: Int= 0

    fun onEvent(event: ManageFinanceEvent) {
        when (event) {
            ManageFinanceEvent.Create -> {
                val isNameValid = _uiState.value.name.validate()
                val isTypeValid = _uiState.value.type.validate()
                val isCategoryValid = _uiState.value.category.validate()
                val isSourceValid = _uiState.value.source.validate()
                val isAmountValid = _uiState.value.amount.validate()
                if (isNameValid && isTypeValid && isCategoryValid && isSourceValid && isAmountValid) {
                    addFinance()
                }
            }
            ManageFinanceEvent.Update -> {
                val isNameValid = _uiState.value.name.validate()
                val isTypeValid = _uiState.value.type.validate()
                val isCategoryValid = _uiState.value.category.validate()
                val isSourceValid = _uiState.value.source.validate()
                val isAmountValid = _uiState.value.amount.validate()
                if (isNameValid && isTypeValid && isCategoryValid && isSourceValid && isAmountValid) {
                    updateFinance()
                }
            }

            ManageFinanceEvent.RefreshFinance -> {
                getFinance(financeId)
            }

            ManageFinanceEvent.Delete -> {
                deleteFinance()
            }
        }
    }

    private fun deleteFinance() {
        viewModelScope.launch {
            financeRepository.deleteFinance(financeId).flowOn(Dispatchers.IO).collect{
                result ->
                when (result) {
                    is Resource.Error -> {
                        _uiState.value = _uiState.value.copy(resource = result)
                    }
                    is Resource.Success -> {
                        _uiState.value = _uiState.value.copy(returnFromDelete = true)
                    }
                    is Resource.Loading -> {
                        _uiState.value = _uiState.value.copy(resource = result)
                    }
                }
            }
        }
    }

    private fun updateFinance() {
        viewModelScope.launch {
            val request = ManageFinanceRequest(
                name = _uiState.value.name.text,
                type = _uiState.value.type.text,
                category = _uiState.value.category.text,
                source = _uiState.value.source.text,
                amount = _uiState.value.amount.text.toInt()
            )
            financeRepository.updateFinance(financeId, request).flowOn(Dispatchers.IO).collect{
                result ->
                _uiState.value = _uiState.value.copy(resource = result)
                when (result) {
                    is Resource.Error -> {}
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun addFinance() {
        viewModelScope.launch {
            val request = ManageFinanceRequest(
                name = _uiState.value.name.text,
                type = _uiState.value.type.text,
                category = _uiState.value.category.text,
                source = _uiState.value.source.text,
                amount = _uiState.value.amount.text.toInt()
            )
            financeRepository.addFinance(request).flowOn(Dispatchers.IO).collect{
                result ->
                _uiState.value = _uiState.value.copy(resource = result)
                when (result) {
                    is Resource.Error -> {}
                    is Resource.Success -> {}
                    is Resource.Loading -> {}
                }
            }
        }
    }

    init {
        val financeId: Int? = savedStateHandle[Params.FINANCE_ID]
        financeId?.let{
            this.financeId = financeId
            getFinance(financeId)
        }
    }

    private fun getFinance(id: Int) {
        viewModelScope.launch {
            financeRepository.finance(id).flowOn(Dispatchers.IO).collect{
                result ->
                _uiState.value = _uiState.value.copy(financeResource = result)
                when (result) {
                    is Resource.Error -> {}
                    is Resource.Success -> viewModelScope.launch {
                        handleGetFinanceSuccess(result)
                    }
                    is Resource.Loading -> {}
                }
            }
        }
    }

    private fun handleGetFinanceSuccess(result: Resource.Success<BaseApiResponse<FinanceModel, String>>) {
        result.data.data?.let { finance ->
            _uiState.value.apply {
                name.text = finance.name
                type.text = finance.type
                category.text = finance.category
                source.text = finance.source
                amount.text = finance.amount.toString()
            }
        }
    }
}