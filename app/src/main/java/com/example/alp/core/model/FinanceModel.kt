package com.example.alp.core.model

import com.google.gson.annotations.SerializedName

data class FinanceModel(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("category") val category: String,
    @SerializedName("source") val source: String,
    @SerializedName("amount") val amount: Int,
    @SerializedName("formatted_amount") val formattedAmount: String,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("human_diff") val humanDiff: String
)