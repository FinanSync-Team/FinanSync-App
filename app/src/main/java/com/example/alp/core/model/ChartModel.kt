package com.example.alp.core.model

import androidx.compose.ui.graphics.Color
import com.example.alp.R
import com.example.alp.ui.screens.main.budgeting.PieChartData
import com.google.gson.annotations.SerializedName

data class ChartModel(
    @SerializedName("Transport")
    val transport: DetailChartModel,
    @SerializedName("Bills")
    val bills: DetailChartModel,
    @SerializedName("Food")
    val food: DetailChartModel,
    @SerializedName("Other")
    val other: DetailChartModel,
){
    fun convertToPieChartModel(): List<PieChartData> {
        return listOf(
            PieChartData("Transport", transport.percentage, Color(0xFF6347EB), R.drawable.ic_transport),
            PieChartData("Bills", bills.percentage, Color(0xFFF46040), R.drawable.ic_bills),
            PieChartData("Food", food.percentage, Color(0xFF2B87E3), R.drawable.ic_food),
            PieChartData("Other", other.percentage, Color(0xFF2BE368), R.drawable.ic_other)
        )
    }
}