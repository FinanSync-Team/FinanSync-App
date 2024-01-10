package com.example.alp.ui.screens.finances.finance_list

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alp.abstraction.Resource
import com.example.alp.core.model.FinanceModel
import com.example.alp.ui.components.CustomSnackbarHost
import com.example.alp.ui.components.DefaultTopBar
import com.example.alp.ui.components.LoadingListItem
import com.example.alp.ui.components.SnackbarOnError
import com.example.alp.ui.items.TransactionItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FinanceListScreen(
    onRefresh: Boolean = false,
    state: FinanceListUiState,
    onEvent: (FinanceListEvent) -> Unit,
    back : () -> Unit = {},
    navigateToManageFinance: () -> Unit = {},
    navigateToEditFinance: (Int) -> Unit = {}
){
    LaunchedEffect(onRefresh) {
        if(onRefresh){
            onEvent(FinanceListEvent.Refresh)
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }
    SnackbarOnError(
        snackbarHostState = snackbarHostState,
        resource = state.resource,
        onRetry = { onEvent(FinanceListEvent.Refresh) }
    )
    Scaffold(
        snackbarHost = { CustomSnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToManageFinance) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Finance")
            }
        },
        topBar = {
            DefaultTopBar(
                title = "Finance List",
                onBackClicked = back
            )
        }
    ){
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
        ){
            if(state.resource is Resource.Loading){
                items(10){
                    LoadingListItem()
                    Spacer(modifier = Modifier.height(21.dp))
                }
            } else {
                items(state.finances){ finance ->
                    TransactionItem(finance, onClick = {
                        navigateToEditFinance(finance.id)
                    })
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FinanceListScreenPreview(){
    val finances: List<FinanceModel> = listOf(
        FinanceModel(
            id = 1,
            name = "Makan Soto",
            amount = 100000,
            formattedAmount = "Rp 100.000",
            type = "Expense",
            createdAt = "2021-10-19T23:25:00.000Z",
            updatedAt = "2021-10-19T23:25:00.000Z",
            humanDiff = "2 hours ago",
            category = "Food",
            source = "BCA"
        )
    )
    FinanceListScreen(
        state = FinanceListUiState(
            finances = finances
        ),
        onEvent = {}
    )
}