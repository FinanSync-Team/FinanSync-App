package com.example.alp.ui.screens.finances.monthly_budget

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
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
import com.example.alp.ui.components.LoadingBar
import com.example.alp.ui.components.SnackbarOnError
import com.example.alp.ui.components.TextFieldValidation
import com.example.alp.ui.screens.finances.manage_finance.ManageFinanceEvent
import com.example.alp.ui.theme.RedError
import com.example.alp.ui.theme.buttonColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthlyBudgetScreen(
    refresh: Boolean = false,
    state: MonthlyBudgetUiState,
    onEvent: (MonthlyBudgetEvent) -> Unit,
    back: () -> Unit = {},
    finishBack: () -> Unit = {}
){
    val snackbarHostState = remember { SnackbarHostState() }

    if (state.returnFromSubmit) {
        LaunchedEffect(Unit) {
            snackbarHostState.showSnackbar(
                SnackbarMessage.success(
                    message = "Success Submit Monthly Budget",
                    duration = SnackbarDuration.Short,
                    withDismissAction = true
                )
            )
            finishBack()
        }
    }

    SnackbarOnError(
        snackbarHostState = snackbarHostState,
        resource = state.resource,
    )

    Scaffold(
        snackbarHost = { CustomSnackbarHost(snackbarHostState) },
        topBar = {
            DefaultTopBar(
                title = "Setup Monthly Budget",
                onBackClicked = back
            )
        }
    ) { it ->
        Column(modifier = Modifier.fillMaxSize().padding(it).padding(horizontal = 24.dp), horizontalAlignment = CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.ic_budget),
                contentDescription = "budget Image",
                modifier= Modifier.size(150.dp)
            )
            TextFieldValidation(
                keyboardType = KeyboardType.Number,
                inputState = state.budget,
                label = "Monthly Budget",
                placeholder = "500000",
            )
            Spacer(modifier = Modifier.height(24.dp))

            if (state.resource is Resource.Loading) {
                LoadingBar()
            } else {
                Button(
                    onClick = {
                        onEvent(MonthlyBudgetEvent.Submit)
                    },
                    colors = ButtonDefaults.buttonColors(buttonColor),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(6.dp),
                ) {
                    Text(
                        text = "Save",
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
        }

    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewMonthlyBudgetScreen(){
    MonthlyBudgetScreen(
        state = MonthlyBudgetUiState(),
        onEvent = {}
    )
}