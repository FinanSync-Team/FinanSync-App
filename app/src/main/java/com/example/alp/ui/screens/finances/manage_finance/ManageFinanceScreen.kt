package com.example.alp.ui.screens.finances.manage_finance

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.alp.R
import com.example.alp.abstraction.Resource
import com.example.alp.abstraction.SnackbarMessage
import com.example.alp.ui.components.ConfirmationDialog
import com.example.alp.ui.components.CustomSnackbarHost
import com.example.alp.ui.components.DefaultTopBar
import com.example.alp.ui.components.ExposedDropdownMenu
import com.example.alp.ui.components.LoadingBar
import com.example.alp.ui.components.SnackbarOnError
import com.example.alp.ui.components.TextFieldValidation
import com.example.alp.ui.theme.RedError
import com.example.alp.ui.theme.buttonColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageFinanceScreen(
    state: ManageFinanceUiState,
    onEvent: (ManageFinanceEvent) -> Unit,
    back: () -> Unit = {},
    finishBack: () -> Unit = {}
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val openDialog = remember { mutableStateOf(false) }

    ConfirmationDialog(
        title = "Delete",
        message = "Are you sure want to delete this item?",
        openDialog = openDialog,
        onDismiss = {
            openDialog.value = false
        },
        onConfirm = {
            onEvent(ManageFinanceEvent.Delete)
        },
    )


    SnackbarOnError(
        snackbarHostState = snackbarHostState,
        resource = state.resource,
    )
    if (state.returnFromDelete) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(
                SnackbarMessage.success(
                    message = "Success Delete ${state.type.text.ifBlank { "Finance" }}",
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
                )
            )
            finishBack()
        }
    }
    SnackbarOnError(
        snackbarHostState = snackbarHostState,
        resource = state.financeResource,
        onRetry = { onEvent(ManageFinanceEvent.RefreshFinance) }
    )
    LaunchedEffect(state.resource) {
        if (state.resource is Resource.Success) {
            snackbarHostState.showSnackbar(
                SnackbarMessage.success(
                    message = "Success ${if (state.financeResource is Resource.Success) "Edit" else "Add"} ${state.type.text.ifBlank { "Finance" }}",
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
                )
            )
            finishBack()
        }
    }
    Scaffold(
        snackbarHost = { CustomSnackbarHost(snackbarHostState) },
        topBar = {
            DefaultTopBar(
                title = "${if (state.financeResource is Resource.Success) "Edit" else "Add"} ${state.type.text.ifBlank { "Finance" }}",
                onBackClicked = back
            )
        }
    ) { it ->
        LazyColumn(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 24.dp)
        ) {
            if (state.financeResource is Resource.Loading) {
                item {
                    LoadingBar()
                }
            } else {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.ic_finance),
                        contentDescription = "Finance Image",
                        modifier = Modifier
                            .padding(horizontal = 50.dp)
                            .fillMaxWidth()
                            .height(160.dp)
                            .padding(top = 15.dp)

                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ExposedDropdownMenu(
                        inputState = state.type,
                        items = listOf("Expense", "Income"),
                        label = "Type",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextFieldValidation(
                        inputState = state.name,
                        label = "${state.type.text} Name",
                        placeholder = "Savings Money",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    TextFieldValidation(
                        inputState = state.amount,
                        label = "Amount",
                        placeholder = "120000",
                        keyboardType = KeyboardType.Number,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ExposedDropdownMenu(
                        inputState = state.category,
                        items = listOf("Transport", "Bills", "Food", "Other"),
                        label = "Category",
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    ExposedDropdownMenu(
                        inputState = state.source,
                        items = listOf("BCA", "OVO", "Gopay", "Other"),
                        label = "Source",
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    if (state.resource is Resource.Loading) {
                        LoadingBar()
                    } else {
                        Button(
                            onClick = {
                                if (state.financeResource is Resource.Success)
                                    onEvent(ManageFinanceEvent.Update)
                                else
                                    onEvent(ManageFinanceEvent.Create)
                            },
                            colors = ButtonDefaults.buttonColors(buttonColor),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            shape = RoundedCornerShape(6.dp),
                        ) {
                            Text(
                                text = if (state.financeResource is Resource.Success) "Edit" else "Add",
                                fontWeight = FontWeight.SemiBold
                            )
                        }

                        if (state.financeResource is Resource.Success) {
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    openDialog.value = true
                                },
                                colors = ButtonDefaults.buttonColors(RedError),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(48.dp),
                                shape = RoundedCornerShape(6.dp),
                            ) {
                                Text(
                                    text = "Delete",
                                    fontWeight = FontWeight.SemiBold
                                )
                            }

                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ManageFinanceScreenPreview() {
    ManageFinanceScreen(
        state = ManageFinanceUiState(),
        onEvent = {}
    )
}