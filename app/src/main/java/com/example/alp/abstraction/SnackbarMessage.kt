package com.example.alp.abstraction

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.ui.graphics.Color
import com.example.alp.ui.theme.BlueInfo
import com.example.alp.ui.theme.GreenSuccess
import com.example.alp.ui.theme.RedError

data class SnackbarMessage(
    override val message: String,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    val containerColor: Color = Color.White,
    val contentColor: Color = Color.Red
): SnackbarVisuals {
    companion object{
        fun error(
            message: String,
            duration: SnackbarDuration = SnackbarDuration.Short,
            actionLabel: String? = null,
            withDismissAction: Boolean = false
        ): SnackbarMessage {
            return SnackbarMessage(
                message = message,
                duration = duration,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                containerColor = RedError,
                contentColor = Color.White
            )
        }

        fun success(
            message: String,
            duration: SnackbarDuration = SnackbarDuration.Short,
            actionLabel: String? = null,
            withDismissAction: Boolean = false
        ): SnackbarMessage {
            return SnackbarMessage(
                message = message,
                duration = duration,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                containerColor = GreenSuccess,
                contentColor = Color.White
            )
        }

        fun info(
            message: String,
            duration: SnackbarDuration = SnackbarDuration.Short,
            actionLabel: String? = null,
            withDismissAction: Boolean = false
        ): SnackbarMessage {
            return SnackbarMessage(
                message = message,
                duration = duration,
                actionLabel = actionLabel,
                withDismissAction = withDismissAction,
                containerColor = BlueInfo,
                contentColor = Color.White
            )
        }
    }
}