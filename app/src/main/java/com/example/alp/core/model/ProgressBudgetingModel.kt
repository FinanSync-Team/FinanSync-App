package com.example.alp.core.model

import com.google.gson.annotations.SerializedName

data class ProgressBudgetingModel(
    @SerializedName("category")
    val category: String,
    @SerializedName("total")
    val total: Int,
    @SerializedName("detail")
    val detail: List<DetailProgressBudgetingModel>
)
