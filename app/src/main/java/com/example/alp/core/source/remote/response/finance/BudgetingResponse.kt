package com.example.alp.core.source.remote.response.finance

import com.example.alp.core.model.ChartModel
import com.example.alp.core.model.MonthlyBudgetingModel
import com.example.alp.core.model.ProgressBudgetingModel
import com.google.gson.annotations.SerializedName

data class BudgetingResponse(
    @SerializedName("chart")
    val chart: ChartModel,
    @SerializedName("monthly_budgeting")
    val monthlyBudgeting: MonthlyBudgetingModel,
    @SerializedName("progress")
    val progress: List<ProgressBudgetingModel>
)