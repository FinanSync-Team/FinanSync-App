package com.example.alp.core.source.remote.response.finance

import com.example.alp.core.model.BalanceModel
import com.example.alp.core.model.BudgetModel
import com.example.alp.core.model.FinanceModel
import com.example.alp.core.model.UserModel
import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("user")
    val user: UserModel,
    @SerializedName("finances")
    val finances: List<FinanceModel>,
    @SerializedName("balance") val balance: BalanceModel,
    @SerializedName("budget") val budget: BudgetModel
)