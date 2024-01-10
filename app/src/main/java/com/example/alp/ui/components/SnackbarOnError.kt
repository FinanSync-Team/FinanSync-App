package com.example.alp.ui.components


import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.alp.abstraction.HttpResult
import com.example.alp.abstraction.Resource
import com.example.alp.abstraction.SnackbarMessage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SnackbarOnError(
    snackbarHostState: SnackbarHostState,
    resource: Resource<Any>?,
    actionLabel: String? = null,
    withDismissAction: Boolean = true,
    onRetry: () -> Unit = {}
) {
    if (resource is Resource.Error && resource.message.isNotEmpty()) {
        LaunchedEffect(snackbarHostState) {
            when(resource.cause){
                HttpResult.NO_CONNECTION, HttpResult.TIMEOUT, HttpResult.SERVER_ERROR -> {
                    val result = snackbarHostState.showSnackbar(
                        SnackbarMessage.error(
                            message = resource.message,
                            duration = SnackbarDuration.Indefinite,
                            actionLabel = "Retry",
                            withDismissAction = false
                        )
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        snackbarHostState.currentSnackbarData?.dismiss()
                        onRetry()
                    }
                }
                else -> snackbarHostState.showSnackbar(
                    SnackbarMessage.error(
                        message = resource.message,
                        duration = SnackbarDuration.Short,
                        actionLabel = actionLabel,
                        withDismissAction = withDismissAction
                    )
                )
            }

        }
    }
}