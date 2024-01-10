package com.example.alp.core.repository

import com.example.alp.abstraction.Resource
import com.example.alp.core.model.FinanceModel
import com.example.alp.core.repository.contract.FinanceRepository
import com.example.alp.core.source.remote.RemoteDataSource
import com.example.alp.core.source.remote.request.finance.ManageFinanceRequest
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.response.finance.BudgetingResponse
import com.example.alp.core.source.remote.response.finance.HomeResponse
import com.example.alp.core.source.remote.response.finance.ManageFinanceErrors
import com.example.alp.core.source.remote.services.FinanceService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FinanceRepositoryImpl @Inject constructor(
    private val api: FinanceService
): FinanceRepository, RemoteDataSource() {
    override suspend fun finances(): Flow<Resource<BaseApiResponse<List<FinanceModel>, String>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.finances()
        }
        emit(response)
    }

    override suspend fun finance(id: Int): Flow<Resource<BaseApiResponse<FinanceModel, String>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.finance(id)
        }
        emit(response)
    }

    override suspend fun budgeting(): Flow<Resource<BaseApiResponse<BudgetingResponse, String>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.budgeting()
        }
        emit(response)
    }

    override suspend fun home(): Flow<Resource<BaseApiResponse<HomeResponse, String>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.home()
        }
        emit(response)
    }

    override suspend fun addFinance(request: ManageFinanceRequest): Flow<Resource<BaseApiResponse<FinanceModel, ManageFinanceErrors>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.addFinance(request)
        }
        emit(response)
    }

    override suspend fun updateFinance(
        id: Int,
        request: ManageFinanceRequest
    ): Flow<Resource<BaseApiResponse<FinanceModel, ManageFinanceErrors>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.updateFinance(id, request)
        }
        emit(response)
    }

    override suspend fun deleteFinance(id: Int): Flow<Resource<BaseApiResponse<String, String>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.deleteFinance(id)
        }
        emit(response)
    }

    override suspend fun setMonthlyBudget(budget: Int): Flow<Resource<BaseApiResponse<String, List<String>>>> = flow {
        emit(Resource.Loading())
        val response = safeApiCall {
            api.setMonthlyBudget(budget)
        }
        emit(response)
    }
}