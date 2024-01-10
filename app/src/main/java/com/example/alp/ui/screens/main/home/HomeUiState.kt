package com.example.alp.ui.screens.main.home

import com.example.alp.abstraction.Resource
import com.example.alp.core.model.FinanceModel

data class HomeUiState(
    val resource: Resource<Any>? = null,
    val name: String = "",
    val email: String = "",
    val balance: String = "",
    val income: String = "",
    val expense: String = "",
    val finances: List<FinanceModel> = listOf()
)