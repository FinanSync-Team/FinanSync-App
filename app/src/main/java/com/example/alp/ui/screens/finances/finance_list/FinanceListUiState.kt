package com.example.alp.ui.screens.finances.finance_list

import com.example.alp.abstraction.Resource
import com.example.alp.core.model.FinanceModel

data class FinanceListUiState(
    val resource: Resource<Any>? = null,
    val finances: List<FinanceModel> = listOf()
)