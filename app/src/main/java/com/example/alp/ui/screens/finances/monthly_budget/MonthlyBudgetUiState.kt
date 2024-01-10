package com.example.alp.ui.screens.finances.monthly_budget

import com.example.alp.abstraction.Resource
import com.example.alp.ui.components.TextFieldState

data class MonthlyBudgetUiState(
    val resource: Resource<Any>? = null,
    val budget: TextFieldState = TextFieldState("required|numeric"),
    val returnFromSubmit: Boolean = false,
)