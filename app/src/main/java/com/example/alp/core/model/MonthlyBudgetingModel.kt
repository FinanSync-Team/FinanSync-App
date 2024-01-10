package com.example.alp.core.model

import com.google.gson.annotations.SerializedName

data class MonthlyBudgetingModel(
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("used_budget")
    val usedBudget: Int,
    @SerializedName("formatted_used_budget")
    val formattedUsedBudget: String,
    @SerializedName("left_budget")
    val leftBudget: Int,
    @SerializedName("formatted_left_budget")
    val formattedLeftBudget: String,
)
