package com.example.alp.ui.screens.finances.manage_finance

import com.example.alp.abstraction.Resource
import com.example.alp.core.model.FinanceModel
import com.example.alp.ui.components.TextFieldState

data class ManageFinanceUiState(
    val resource: Resource<Any>? = null,
    val financeResource: Resource<Any>? = null,
    val returnFromDelete: Boolean = false,
    val name: TextFieldState = TextFieldState("required|max:50"),
    val type: TextFieldState = TextFieldState("required|in:Income,Expense"),
    val category: TextFieldState = TextFieldState("required|in:Transport,Bills,Food,Other"),
    val source: TextFieldState = TextFieldState("required|in:BCA,Gopay,OVO,Other"),
    val amount: TextFieldState = TextFieldState("required|numeric"),
)