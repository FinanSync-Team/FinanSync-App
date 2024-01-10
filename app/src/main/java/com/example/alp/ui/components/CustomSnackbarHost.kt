package com.example.alp.ui.components

import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import com.example.alp.abstraction.SnackbarMessage

@Composable
fun CustomSnackbarHost(snackbarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackbarHostState, snackbar = { snackbarData: SnackbarData ->
        val customVisuals = snackbarData.visuals as? SnackbarMessage
        if (customVisuals != null) {
            Snackbar(
                snackbarData = snackbarData,
                contentColor = customVisuals.contentColor,
                containerColor = customVisuals.containerColor
            )
        } else {
            Snackbar(snackbarData = snackbarData)
        }
    })
}