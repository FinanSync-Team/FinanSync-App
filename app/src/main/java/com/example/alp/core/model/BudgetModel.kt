package com.example.alp.core.model

import com.google.gson.annotations.SerializedName

data class BudgetModel(
    @SerializedName("expense") val expense: Int,
    @SerializedName("formatted_expense") val formattedExpense: String,
    @SerializedName("income") val income: Int,
    @SerializedName("formatted_income") val formattedIncome: String,
)
