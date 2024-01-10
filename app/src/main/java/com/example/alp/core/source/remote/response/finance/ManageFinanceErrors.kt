package com.example.alp.core.source.remote.response.finance

data class ManageFinanceErrors(
    val name: List<String>? = null,
    val type: List<String>? = null,
    val category: List<String>? = null,
    val source: List<String>? = null,
    val amount: List<String>? = null,
)