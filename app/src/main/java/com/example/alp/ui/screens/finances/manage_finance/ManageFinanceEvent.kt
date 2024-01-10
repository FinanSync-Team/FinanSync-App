package com.example.alp.ui.screens.finances.manage_finance

sealed class ManageFinanceEvent {
    object Create: ManageFinanceEvent()
    object Update: ManageFinanceEvent()
    object Delete: ManageFinanceEvent()
    object RefreshFinance: ManageFinanceEvent()
}