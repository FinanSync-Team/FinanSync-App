package com.example.alp.ui.screens.main.budgeting

import com.example.alp.abstraction.Resource
import com.example.alp.core.model.ChartModel
import com.example.alp.core.model.MonthlyBudgetingModel
import com.example.alp.core.model.ProgressBudgetingModel

data class BudgetingUiState(
    val resource: Resource<Any>? = null,
    val name: String = "",
    val email: String = "",
    val progress: List<ProgressBudgetingModel> = emptyList(),
    val monthlyBudgeting: MonthlyBudgetingModel? = null,
    val chart: ChartModel? = null
)