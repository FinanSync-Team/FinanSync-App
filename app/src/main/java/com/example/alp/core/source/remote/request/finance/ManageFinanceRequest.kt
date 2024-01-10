package com.example.alp.core.source.remote.request.finance

data class ManageFinanceRequest(
    val name: String,
    val type: String,
    val category: String,
    val source: String,
    val amount: Int,
)