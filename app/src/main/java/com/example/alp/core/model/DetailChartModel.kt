package com.example.alp.core.model

import com.google.gson.annotations.SerializedName

data class DetailChartModel(
    @SerializedName("percentage")
    val percentage: Float,
    @SerializedName("amount")
    val amount: Int,
)