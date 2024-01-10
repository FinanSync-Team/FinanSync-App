package com.example.alp.core.model

import com.google.gson.annotations.SerializedName

data class DetailProgressBudgetingModel(
    @SerializedName("title")
    val title: String,
    @SerializedName("category")
    val category: String,
    @SerializedName("total")
    val total: Int,
)