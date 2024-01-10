package com.example.alp.core.source.remote.services

import com.example.alp.core.model.FinanceModel
import com.example.alp.core.source.remote.request.finance.ManageFinanceRequest
import com.example.alp.core.source.remote.response.BaseApiResponse
import com.example.alp.core.source.remote.response.finance.BudgetingResponse
import com.example.alp.core.source.remote.response.finance.HomeResponse
import com.example.alp.core.source.remote.response.finance.ManageFinanceErrors
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface FinanceService {
    @GET("finances")
    suspend fun finances(): BaseApiResponse<List<FinanceModel>, String>

    @GET("finances/{id}")
    suspend fun finance(@Path("id") id: Int): BaseApiResponse<FinanceModel, String>

    @GET("finances/home")
    suspend fun home(): BaseApiResponse<HomeResponse, String>

    @POST("finances")
    suspend fun addFinance(@Body request: ManageFinanceRequest): BaseApiResponse<FinanceModel, ManageFinanceErrors>

    @PUT("finances/{id}")
    suspend fun updateFinance(@Path("id") id: Int, @Body request: ManageFinanceRequest): BaseApiResponse<FinanceModel, ManageFinanceErrors>

    @DELETE("finances/{id}")
    suspend fun deleteFinance(@Path("id") id: Int): BaseApiResponse<String, String>

    @POST("finances/budgeting/setup")
    @FormUrlEncoded
    suspend fun setMonthlyBudget(@Field("amount") amount: Int): BaseApiResponse<String, List<String>>

    @GET("finances/budgeting")
    suspend fun budgeting(): BaseApiResponse<BudgetingResponse, String>
}