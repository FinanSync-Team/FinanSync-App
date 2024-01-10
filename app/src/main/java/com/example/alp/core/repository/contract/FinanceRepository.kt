package com.example.alp.core.repository.contract

import com.example.alp.abstraction.Resource
import com.example.alp.core.model.FinanceModel
import com.example.alp.core.source.remote.request.finance.ManageFinanceRequest
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.response.finance.BudgetingResponse
import com.example.alp.core.source.remote.response.finance.HomeResponse
import com.example.alp.core.source.remote.response.finance.ManageFinanceErrors
import kotlinx.coroutines.flow.Flow

interface FinanceRepository {
    suspend fun finances(): Flow<Resource<BaseApiResponse<List<FinanceModel>, String>>>
    suspend fun finance(id: Int): Flow<Resource<BaseApiResponse<FinanceModel, String>>>

    suspend fun budgeting(): Flow<Resource<BaseApiResponse<BudgetingResponse, String>>>
    suspend fun home(): Flow<Resource<BaseApiResponse<HomeResponse, String>>>
    suspend fun addFinance(request: ManageFinanceRequest): Flow<Resource<BaseApiResponse<FinanceModel, ManageFinanceErrors>>>
    suspend fun updateFinance(id: Int, request: ManageFinanceRequest): Flow<Resource<BaseApiResponse<FinanceModel, ManageFinanceErrors>>>
    suspend fun deleteFinance(id: Int): Flow<Resource<BaseApiResponse<String, String>>>

    suspend fun setMonthlyBudget(budget: Int) : Flow<Resource<BaseApiResponse<String, List<String>>>>

}