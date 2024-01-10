package com.example.alp.core.model

import com.google.gson.annotations.SerializedName

data class BalanceModel(
    @SerializedName("value") val value: Int,
    @SerializedName("formatted_value") val formattedValue: String,
)